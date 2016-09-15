package de.ottensa.rockpaperscissors.controller;

import static org.junit.Assert.*;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.ottensa.rockpaperscissors.RockPaperScissorsApplication;
import de.ottensa.rockpaperscissors.model.Game;
import de.ottensa.rockpaperscissors.model.Move;
import de.ottensa.rockpaperscissors.model.Status;
import de.ottensa.rockpaperscissors.repositories.GameRepo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(RockPaperScissorsApplication.class)
@WebIntegrationTest(randomPort = true)
public class GameControllerTest {

	@Value("${local.server.port}")
    int port;
	
	@Autowired
	GameRepo gameRepo;
	
	TestRestTemplate template = new TestRestTemplate();

	@Before
	public void setUp() {
		gameRepo.deleteAll();
	}
	
	private String getBaseUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void testCreateNewGame() {
		URI uri = template.postForLocation(getBaseUrl() + "/games", null);
		assertNotNull("uri should contain the location of the new game!", uri);
		
		ResponseEntity<Game> entity = template.getForEntity(uri, Game.class);
		assertEquals("The request to the new game should return status 200", HttpStatus.OK, entity.getStatusCode());
		
		Game game = entity.getBody();
		assertNotNull("The response body should contain the new game", game);
		assertEquals("The status of the game should be 'started'", Status.started, game.getStatus());
		assertNull("There should be no move for playerOne", game.getPlayerOne());
		assertNull("There should be no move for playerTwo", game.getPlayerTwo());
		assertNull("There should be no result", game.getResult());
	}
	
	@Test
	public void testAbortGame() {
		URI uri = template.postForLocation(getBaseUrl() + "/games", null);
		template.delete(uri);

		ResponseEntity<Game> entity = template.getForEntity(uri, Game.class);
		Game game = entity.getBody();
		
		assertEquals("The status of the game should be 'aborted'", Status.aborted, game.getStatus());
	}

	@Test
	public void testPlayGame() {
		URI uri = template.postForLocation(getBaseUrl() + "/games", null);
		template.put(uri, Move.Rock);

		ResponseEntity<Game> entity = template.getForEntity(uri, Game.class);
		Game game = entity.getBody();
		
		assertNotNull("The game must not be null", game);
		assertEquals("The status of the game should be 'finished'", Status.finished, game.getStatus());
		assertNotNull("There should be a move for playerOne", game.getPlayerOne());
		assertEquals("The move of playeOne should be 'Rock'", Move.Rock, game.getPlayerOne());
		assertNotNull("There should be a move for playerTwo", game.getPlayerTwo());
		assertNotNull("There should be a result", game.getResult());
	}
	
	@Test
	public void testPlayAnInvalidMove() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Integer> req = new HttpEntity<Integer>(42,headers);
		
		URI uri = template.postForLocation(getBaseUrl() + "/games", null);
		ResponseEntity<Void> response = template.exchange(uri, HttpMethod.PUT, req, Void.class);
		assertEquals("This should be a 'Bad Request (400)'", HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void testPlayNullAsAMove() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Void> req = new HttpEntity<Void>(null,headers);
		
		URI uri = template.postForLocation(getBaseUrl() + "/games", null);
		ResponseEntity<Void> response = template.exchange(uri, HttpMethod.PUT, req, Void.class);
		assertEquals("This should be a 'Bad Request (400)'", HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void testGetGameWithWrongId() {
		ResponseEntity<Void> entity = template.getForEntity(getBaseUrl() + "/games/1", Void.class);
		assertEquals("There is no entity, so the result should be 404", HttpStatus.NOT_FOUND, entity.getStatusCode());
	}
	
	@Test
	public void testGetGameWithIdInWrongFormat() {
		ResponseEntity<Void> entity = template.getForEntity(getBaseUrl() + "/games/one", Void.class);
		assertEquals("The format of the id is wrong, so it is a bad request (400)", HttpStatus.BAD_REQUEST, entity.getStatusCode());
	}
	
	@Test
	public void testAbortNonExistingGame() {
		ResponseEntity<Void> response = template.exchange(getBaseUrl() + "/games/1", HttpMethod.DELETE, null, Void.class);
		assertEquals("This game does not exist, so 404 is expected", HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void testAbortAbortedGame() {
		URI uri = template.postForLocation(getBaseUrl() + "/games", null);
		template.delete(uri);

		ResponseEntity<Void> response = template.exchange(uri, HttpMethod.DELETE, null, Void.class);
		
		assertEquals("The game is already aborted, it can't be aborted twice", HttpStatus.CONFLICT, response.getStatusCode());

	}
	
	@Test
	public void testAbortFinishedGame() {
		URI uri = template.postForLocation(getBaseUrl() + "/games", null);
		template.put(uri, Move.Rock);

		ResponseEntity<Void> response = template.exchange(uri, HttpMethod.DELETE, null, Void.class);
		
		assertEquals("The game is already finished, so it can't be aborted", HttpStatus.CONFLICT, response.getStatusCode());
	}
	
	@Test
	public void testPlayAnAbortedGame() {
		URI uri = template.postForLocation(getBaseUrl() + "/games", null);
		template.delete(uri);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Move> req = new HttpEntity<Move>(Move.Rock,headers);
		
		ResponseEntity<Void> response = template.exchange(uri, HttpMethod.PUT, req, Void.class);
		assertEquals("The game is already aborted, making a move is not allowed", HttpStatus.CONFLICT, response.getStatusCode());
	}
	
	@Test
	public void testPlayAFinishedGame() {
		URI uri = template.postForLocation(getBaseUrl() + "/games", null);
		template.put(uri, Move.Rock);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Move> req = new HttpEntity<Move>(Move.Rock,headers);
		
		ResponseEntity<Void> response = template.exchange(uri, HttpMethod.PUT, req, Void.class);
		assertEquals("The game is already finished, making a second move is not allowed", HttpStatus.CONFLICT, response.getStatusCode());
	}
}

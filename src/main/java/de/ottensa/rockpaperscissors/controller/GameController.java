package de.ottensa.rockpaperscissors.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.ottensa.rockpaperscissors.RockPaperScissorsApplication;
import de.ottensa.rockpaperscissors.core.GameEngine;
import de.ottensa.rockpaperscissors.model.Game;
import de.ottensa.rockpaperscissors.model.GameDoesNotExistException;
import de.ottensa.rockpaperscissors.model.GameIsReadOnlyException;
import de.ottensa.rockpaperscissors.model.Move;
import de.ottensa.rockpaperscissors.model.Result;
import de.ottensa.rockpaperscissors.model.Status;
import de.ottensa.rockpaperscissors.repositories.GameRepo;
import de.ottensa.rockpaperscissors.util.RandomEnum;

/**
 * This is the REST controller for {@link RockPaperScissorsApplication}. 
 * It handles game creation and the requests to a certain game (get information, play and abort);
 *   
 * @author ottensa
 *
 */
@RestController
@RequestMapping(value = "/games")
public class GameController {
	
	private GameRepo gameRepo;
	private GameEngine gameEngine;
	
	@Autowired
	public GameController(GameEngine gameEngine, GameRepo gameRepo) {
		this.gameEngine = gameEngine;
		this.gameRepo = gameRepo;
	}

	/**
	 * Controller method that takes care of game creation.
	 * Listens to: POST /games
	 * 
	 * @return {@link HttpHeaders} with path to the created game in the location tag
	 */
	@RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseStatus(HttpStatus.CREATED)
	HttpHeaders startNewGame() {
		Game game = new Game();
		gameRepo.save(game);
		
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(GameController.class).slash(game.getId()).toUri());
		
        return headers;
	}
	
	/**
	 * Controller method that returns a requested {@link Game} 
	 * Listens to: GET /games/{id}
	 * 
	 * @param id of the requested {@link Game}
	 * @return the requested {@link Game}
	 * @throws GameDoesNotExistException if the requested {@link Game} does not exist
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	Game getGame(@PathVariable long id) throws GameDoesNotExistException {
		if(!gameRepo.exists(id)){
			throw new GameDoesNotExistException(id);
		}
		
		return gameRepo.findOne(id);
	}
	
	/**
	 * Controller method that takes care of game abortion
	 * Listens to: DELETE /games/{id}
	 * 
	 * @param id of the requested {@link Game}
	 * @throws GameDoesNotExistException if the requested {@link Game} does not exist
	 * @throws GameIsReadOnlyException if the requested game does not allow changes anymore
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
	void abortGame(@PathVariable long id) throws GameDoesNotExistException, GameIsReadOnlyException {
		if (!gameRepo.exists(id)) {
			throw new GameDoesNotExistException(id);
		}
		
		Game game = gameRepo.findOne(id);
		if (game.getStatus() != Status.started) {
			throw new GameIsReadOnlyException(id);
		}
		
		game.setStatus(Status.aborted);
		
		gameRepo.save(game);
	}
	
	/**
	 * Controller method that takes care of the game play.
	 * It takes the input from the user and a computed random value 
	 * and finds the result for this match.
	 * Listens to: PUT /games/{id}
	 * 
	 * @param id of the requested {@link Game}
	 * @param playerOne is the {@link Move} the user made
	 * @throws GameDoesNotExistException if the requested {@link Game} does not exist
	 * @throws GameIsReadOnlyException if the requested game does not allow changes anymore
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	void play(@PathVariable long id, @RequestBody Move playerOne) throws GameDoesNotExistException, GameIsReadOnlyException {
		if(!gameRepo.exists(id)){
			throw new GameDoesNotExistException(id);
		}
		
		Game game = gameRepo.findOne(id);
		if (game.getStatus() != Status.started) {
			throw new GameIsReadOnlyException(id);
		}
		
		Move playerTwo = RandomEnum.getValue(Move.class);
		Result result = gameEngine.evaluateMoves(playerOne, playerTwo);
		
		game.setPlayerOne(playerOne);
		game.setPlayerTwo(playerTwo);
		game.setResult(result);
		game.setStatus(Status.finished);
		
		gameRepo.save(game);
	}
	
}

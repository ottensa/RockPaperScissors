package de.ottensa.rockpaperscissors.core;

import org.junit.Before;
import org.junit.Test;

import de.ottensa.rockpaperscissors.model.Move;

public class GameEngineNegativeTest {
	
	private GameEngine candidate;

	@Before
	public void setUp() throws Exception {
		candidate = new GameEngine();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBothMovesAreNull() {
		candidate.evaluateMoves(null, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFirstMoveIsNull() {
		candidate.evaluateMoves(null, Move.Rock);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSecondMoveIsNull() {
		candidate.evaluateMoves(Move.Rock, null);
	}

}

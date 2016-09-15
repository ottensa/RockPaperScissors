package de.ottensa.rockpaperscissors.core;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.ottensa.rockpaperscissors.core.GameEngine;
import de.ottensa.rockpaperscissors.model.Result;
import de.ottensa.rockpaperscissors.model.Move;

@RunWith(Parameterized.class)
public class GameEnginePositiveTest {
	
	@Parameters(name = "{index}: {0} vs. {1}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{Move.Rock,		Move.Rock,		Result.Draw},
			{Move.Rock,		Move.Paper,		Result.Loose},
			{Move.Rock,		Move.Scissors,	Result.Win},
			{Move.Paper,	Move.Rock,		Result.Win},
			{Move.Paper,	Move.Paper,		Result.Draw},
			{Move.Paper,	Move.Scissors,	Result.Loose},
			{Move.Scissors, Move.Rock,		Result.Loose},
			{Move.Scissors, Move.Paper,		Result.Win},
			{Move.Scissors, Move.Scissors,	Result.Draw},
		});
	}

	private Move playerOne;
	private Move playerTwo;
	private Result expected;
	
	public GameEnginePositiveTest(Move playerOne, Move playerTwo, Result expected) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.expected = expected;
	}

	@Test
	public void testRockPaperScissors() {
		GameEngine candidate = new GameEngine();
		Result actual = candidate.evaluateMoves(playerOne, playerTwo);
		
		assertEquals(expected, actual);
	}

}

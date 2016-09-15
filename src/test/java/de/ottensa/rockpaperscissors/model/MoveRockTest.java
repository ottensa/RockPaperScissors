package de.ottensa.rockpaperscissors.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoveRockTest {
	
	Move candidate = Move.Rock;

	@Test
	public void testRockIsBetterThanPaper() {
		assertFalse("Rock should not be better than Paper", candidate.isBetterThan(Move.Paper));
	}
	
	@Test
	public void testRockIsBetterThanScissors() {
		assertTrue("Rock should be better than Scissors", candidate.isBetterThan(Move.Scissors));
	}
	
	@Test
	public void testRockIsBetterThanRock() {
		assertFalse("Rock should not be better than Rock", candidate.isBetterThan(Move.Rock));
	}
	
	@Test
	public void testRockIsBetterThanNull() {
		assertFalse("Rock should not be better than null", candidate.isBetterThan(null));
	}

}

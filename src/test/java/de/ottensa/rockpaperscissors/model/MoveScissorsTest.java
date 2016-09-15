package de.ottensa.rockpaperscissors.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoveScissorsTest {
	
	Move candidate = Move.Scissors;

	@Test
	public void testScissorsIsBetterThanPaper() {
		assertTrue("Scissors should be better than Paper", candidate.isBetterThan(Move.Paper));
	}
	
	@Test
	public void testScissorsIsBetterThanScissors() {
		assertFalse("Scissors should not be better than Scissors", candidate.isBetterThan(Move.Scissors));
	}
	
	@Test
	public void testScissorsIsBetterThanRock() {
		assertFalse("Scissors should not be better than Rock", candidate.isBetterThan(Move.Rock));
	}
	
	@Test
	public void testScissorsIsBetterThanNull() {
		assertFalse("Scissors should not be better than null", candidate.isBetterThan(null));
	}
}

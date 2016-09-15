package de.ottensa.rockpaperscissors.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoveFountainTest {
	
	Move candidate = Move.Fountain;

	@Test
	public void testFountainIsBetterThanRock() {
		assertTrue("Fountain should be better than Rock", candidate.isBetterThan(Move.Rock));
	}
	
	@Test
	public void testFountainIsBetterThanPaper() {
		assertFalse("Fountain should not be better than Paper", candidate.isBetterThan(Move.Paper));
	}
	
	@Test
	public void testFountainIsBetterThanScissors() {
		assertTrue("Fountain should be better than Scissors", candidate.isBetterThan(Move.Scissors));
	}
	
	@Test
	public void testFountainIsBetterThanFountain() {
		assertFalse("Fountain should not be better than Fountain", candidate.isBetterThan(Move.Fountain));
	}
	
	@Test
	public void testFountainIsBetterThanNull() {
		assertFalse("Fountain should not be better than null", candidate.isBetterThan(null));
	}
}

package de.ottensa.rockpaperscissors.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class MovePaperTest {
	
	Move candidate = Move.Paper;

	@Test
	public void testPaperIsBetterThanPaper() {
		assertFalse("Paper should not be better than Paper", candidate.isBetterThan(Move.Paper));
	}
	
	@Test
	public void testPaperIsBetterThanScissors() {
		assertFalse("Paper should not be better than Scissors", candidate.isBetterThan(Move.Scissors));
	}
	
	@Test
	public void testPaperIsBetterThanRock() {
		assertTrue("Paper should be better than Rock", candidate.isBetterThan(Move.Rock));
	}
	
	@Test
	public void testPaperIsBetterThanFountain() {
		assertTrue("Paper should be better than Fountain", candidate.isBetterThan(Move.Fountain));
	}
	
	@Test
	public void testPaperIsBetterThanNull() {
		assertFalse("Paper should not be better than null", candidate.isBetterThan(null));
	}
}

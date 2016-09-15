package de.ottensa.rockpaperscissors.model;

/**
 * This exception will be thrown when there is a request to a non-existing
 * {@link Game} ressource
 * 
 * @author ottensa
 *
 */
public class GameDoesNotExistException extends Exception {

	private static final long serialVersionUID = 1382098838917960857L;

	public GameDoesNotExistException(long id) {
		super(String.format("The game with id %d does not exist!", id));
	}

}

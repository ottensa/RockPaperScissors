package de.ottensa.rockpaperscissors.model;

/**
 * This exception will be thrown when there is an attempt to change an already
 * finished or aborted {@link Game}
 * 
 * @author ottensa
 *
 */
public class GameIsReadOnlyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2402940690600125385L;

	public GameIsReadOnlyException(long id) {
		super(String.format("The game with id %d does not accept any changes anymore!", id));
	}
}

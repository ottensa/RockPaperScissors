package de.ottensa.rockpaperscissors.core;

import org.springframework.stereotype.Component;

import de.ottensa.rockpaperscissors.model.Game;
import de.ottensa.rockpaperscissors.model.Move;
import de.ottensa.rockpaperscissors.model.Result;

/**
 * This component is the {@link GameEngine} that computes the result of a {@link Game}
 * @author ottensa
 *
 */
@Component
public class GameEngine {

	/**
	 * Takes two {@link Move}s and figures out the result of the 
	 * competition from the users perspective.
	 * 
	 * @param playerOne {@link Move} the user selected
	 * @param playerTwo {@link Move} the computer selected
	 * @return {@link Result} from the users perspective
	 */
	public Result evaluateMoves(Move playerOne, Move playerTwo) {
		if (playerOne == null || playerTwo == null) {
			String msg = String.format("The moves must not be null (playerOne: %s, playerTwo: %s)!", playerOne, playerTwo);
			throw new IllegalArgumentException(msg);
		}
		
		Result result = Result.Draw;
		
		if (playerOne.isBetterThan(playerTwo)) {
			result = Result.Win;
		}
		else if (playerTwo.isBetterThan(playerOne)){
			result = Result.Loose;
		}
		
		return result;
	}
}

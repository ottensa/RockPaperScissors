package de.ottensa.rockpaperscissors.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Utility class to randomize enums
 * 
 * @author ottensa
 *
 */
public class RandomEnum {

	/**
	 * private constructor to avoid instantiation
	 */
	private RandomEnum() {}

	/**
	 * Computes a random value of type clazz
	 * 
	 * @param clazz 
	 * @return a randum enum of type clazz
	 */
	public static <T extends Enum<?>> T getValue(Class<T> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("This method expects an enum class as parameter, null is not allowed!");
		}

		T result = null;

		List<T> values = Arrays.asList(clazz.getEnumConstants());

		if (!values.isEmpty()) {
			Collections.shuffle(values);
			result = values.get(0);
		}

		return result;
	}
}

package de.ottensa.rockpaperscissors.model;

/**
 * The set of allowed moves in this game
 * 
 * @author ottensa
 *
 */
public enum Move {

	Rock {
		@Override
		public boolean isBetterThan(Move move) {
			return (Scissors == move);
		}
	},
	
	Paper {
		@Override
		public boolean isBetterThan(Move move) {
			return (Rock == move || Fountain == move);
		}
	},
	
	Scissors {
		@Override
		public boolean isBetterThan(Move move) {
			return (Paper == move);
		}
	},
	
	Fountain {
		@Override
		public boolean isBetterThan(Move move) {
			return (Rock == move || Scissors == move);
		}
	};
	
	public abstract boolean isBetterThan(Move move);
	
}

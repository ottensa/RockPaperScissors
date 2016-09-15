package de.ottensa.rockpaperscissors.repositories;


import org.springframework.data.repository.CrudRepository;

import de.ottensa.rockpaperscissors.model.Game;

/**
 * Abstraction of the underlying database
 *  
 * @author ottensa
 *
 */
public interface GameRepo extends CrudRepository<Game, Long>{

}

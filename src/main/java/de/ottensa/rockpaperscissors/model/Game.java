package de.ottensa.rockpaperscissors.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Representation of the game
 * 
 * @author ottensa
 *
 */
@Entity
public class Game {

	@Id
	@GeneratedValue
	private long id;
	
	private Status status;

	private Move playerOne;
	private Move playerTwo;
	private Result result; 
	
	public Game() {
		this.status = Status.started;
	}
	
	public long getId() {
		return id;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	public Move getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(Move playerOne) {
		this.playerOne = playerOne;
	}

	public Move getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(Move playerTwo) {
		this.playerTwo = playerTwo;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((playerOne == null) ? 0 : playerOne.hashCode());
		result = prime * result + ((playerTwo == null) ? 0 : playerTwo.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (id != other.id)
			return false;
		if (playerOne != other.playerOne)
			return false;
		if (playerTwo != other.playerTwo)
			return false;
		if (result != other.result)
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", status=" + status + ", playerOne=" + playerOne + ", playerTwo=" + playerTwo
				+ ", result=" + result + "]";
	}

}

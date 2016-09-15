package de.ottensa.rockpaperscissors.model;

import org.springframework.http.HttpStatus;

/**
 * Custom error representation that will be transferred to the client
 * 
 * @author ottensa
 *
 */
public class ErrorResponse {

	private final long timestamp;
	private final int status;
	private final String message;
	private final String path;
	
	public ErrorResponse(HttpStatus status, String message, String path) {
		this.timestamp = System.currentTimeMillis();
		this.status = status.value();
		this.message = message;
		this.path = path;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + status;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
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
		ErrorResponse other = (ErrorResponse) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (status != other.status)
			return false;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ErrorResponse [timestamp=" + timestamp + ", status=" + status + ", message=" + message + ", path="
				+ path + "]";
	}
	
}

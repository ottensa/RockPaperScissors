package de.ottensa.rockpaperscissors.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import de.ottensa.rockpaperscissors.model.ErrorResponse;
import de.ottensa.rockpaperscissors.model.GameDoesNotExistException;
import de.ottensa.rockpaperscissors.model.GameIsReadOnlyException;

/**
 * This class sets up the exception handlers for {@link GameController}
 *  
 * @author ottensa
 *
 */
@ControllerAdvice
public class ExceptionHandlerConfiguration {

	/**
	 * @param request the {@link WebRequest} that lead to this {@link Exception}
	 * @param the {@link Exception} itself
	 * @return the description of the {@link Exception} and the appropriate HTTP status
	 */
	private ResponseEntity<ErrorResponse> handleException(WebRequest request, Exception exception, HttpStatus status) {
		ErrorResponse response = new ErrorResponse(status, exception.getMessage(), request.getContextPath());
		return new ResponseEntity<ErrorResponse>(response, status);
	}
	
	/**
	 * {@see #handleException(WebRequest, Exception, HttpStatus)}
	 */
	@ExceptionHandler(GameDoesNotExistException.class)
	public ResponseEntity<ErrorResponse> handleException(WebRequest request, GameDoesNotExistException exception) {
		return handleException(request, exception, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * {@see #handleException(WebRequest, Exception, HttpStatus)}
	 */
	@ExceptionHandler(GameIsReadOnlyException.class)
	public ResponseEntity<ErrorResponse> handleException(WebRequest request, GameIsReadOnlyException exception) {
		return handleException(request, exception, HttpStatus.CONFLICT);
	}
	
	/**
	 * {@see #handleException(WebRequest, Exception, HttpStatus)}
	 */
	@ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
	public ResponseEntity<ErrorResponse> handleException(WebRequest request, MethodArgumentTypeMismatchException exception) {
		return handleException(request, exception, HttpStatus.BAD_REQUEST);
	}
	
}

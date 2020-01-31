package br.com.jitec.quiz.presentation.exception.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.jitec.quiz.business.exception.BusinessValidationException;
import br.com.jitec.quiz.business.exception.DataNotFoundException;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Defining status code 404 for DataNotFoundException
	 * 
	 * @param response
	 * @throws IOException
	 */
	@ExceptionHandler(DataNotFoundException.class)
	public void springHandleNotFound(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value());
	}

	/**
	 * Defining status code 400 for BusinessValidationException
	 * 
	 * @param response
	 * @throws IOException
	 */
	@ExceptionHandler(BusinessValidationException.class)
	public void springHandleBusinessValidationException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

}

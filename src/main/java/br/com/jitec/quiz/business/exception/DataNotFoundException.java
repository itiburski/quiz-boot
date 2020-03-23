package br.com.jitec.quiz.business.exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String MESSAGE = "The %s you were trying to reach was not found";

	public DataNotFoundException(String resource) {
		super(String.format(MESSAGE, resource));
	}

}


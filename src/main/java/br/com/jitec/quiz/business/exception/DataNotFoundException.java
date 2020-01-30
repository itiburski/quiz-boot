package br.com.jitec.quiz.business.exception;

public class DataNotFoundException  extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String message) {
		super(message);
	}

}


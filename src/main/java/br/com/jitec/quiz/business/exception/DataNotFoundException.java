package br.com.jitec.quiz.business.exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String MESSAGE = "%s was not found";

	public DataNotFoundException(@SuppressWarnings("rawtypes") Class clazz) {
		super(String.format(MESSAGE, clazz.getSimpleName()));
	}

}


package br.com.jitec.quiz.business.precondition;

import br.com.jitec.quiz.business.exception.DataNotFoundException;

public class BusinessPreconditions {

	/**
	 * Check if some value was found, otherwise throw exception.
	 * 
	 * @param expression has value true if found, otherwise false
	 * @throws DataNotFoundException if expression is false, means value not found.
	 */
	public static <T> T checkFound(final T resource) {
		if (resource == null) {
			throw new DataNotFoundException();
		}

		return resource;
	}

}

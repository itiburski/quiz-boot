package br.com.jitec.quiz.business.precondition;

import br.com.jitec.quiz.business.exception.DataNotFoundException;

public class BusinessPreconditions {

	/**
	 * Check if some value was found, otherwise throw exception.
	 * 
	 * @param resource     resource to be evaluated whether it is null or not
	 * @param resourceName the name that will appear in the exception message when
	 *                     the resource is null
	 * @throws DataNotFoundException if resource is null, means value not found.
	 */
	public static <T> T checkFound(final T resource, final String resourceName) {
		if (resource == null) {
			throw new DataNotFoundException(
					"The " + resourceName + " you were trying to reach was not found");
		}

		return resource;
	}

}

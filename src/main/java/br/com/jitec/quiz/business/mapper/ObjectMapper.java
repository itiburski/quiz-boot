package br.com.jitec.quiz.business.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;

public class ObjectMapper {

	// alternatives to ModelMapper

	// https://amydegregorio.com/2019/01/18/bean-mapping-alternatives-to-modelmapper/
	// https://www.reddit.com/r/java/comments/dt86ul/best_object_mapping_frameworks_for_java/
	// https://www.baeldung.com/java-performance-mapping-frameworks
	// https://www.baeldung.com/mapstruct

	private static ModelMapper modelMapper = new ModelMapper();

	/**
	 * Model mapper property setting are specified in the following block. Default
	 * property matching strategy is set to Strict see {@link MatchingStrategies}
	 * Custom mappings are added using {@link ModelMapper#addMappings(PropertyMap)}
	 */
	static {
//		modelMapper = new ModelMapper();
//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.getConfiguration().setFieldMatchingEnabled(true);
		modelMapper.getConfiguration().setFieldAccessLevel(AccessLevel.PRIVATE);
		modelMapper.getConfiguration().setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
	}

	/**
     * Hide from public usage.
     */
	private ObjectMapper() {
    }

	/**
	 * Maps {@code entity} into a new instance of {@code outClass} type.
	 *
	 * @param <S>      type of source object to map from.
	 * @param <D>      type of result object.
	 * @param entity   entity that needs to be mapped.
	 * @param outClass class of result object. <strong>Note:</strong>
	 *                 {@code outClass} object <ins>must have</ins> default
	 *                 constructor with no arguments
	 * @return new object of {@code outClass} type.
	 */
	public static <S, D> D map(final S entity, Class<D> outClass) {
		return modelMapper.map(entity, outClass);
	}

	/**
	 * Maps all {@code entityCollection} elements into a new {@code outClass} list
	 *
	 * @param <S>              type of entity in {@code entityCollection}
	 * @param <D>              type of objects in result list
	 * @param entityCollection Collection of entities that needs to be mapped
	 * @param outClass         class of result list element. <strong>Note:</strong>
	 *                         {@code outClass} object <ins>must have</ins> default
	 *                         constructor with no arguments
	 * @return list of mapped object with {@code outClass} type.
	 */
	public static <S, D> List<D> mapAll(final Collection<S> entityCollection, Class<D> outClass) {
		return entityCollection.stream().map(entity -> map(entity, outClass)).collect(Collectors.toList());
	}

	private static <E> List<E> makeList(Iterable<E> iter) {
		List<E> list = new ArrayList<E>();
		for (E item : iter) {
			list.add(item);
		}
		return list;
	}

	/**
	 * Maps all {@code entityIterable} elements into a new {@code outClass} list
	 *
	 * @param <S>            type of entity in {@code entityIterable}
	 * @param <D>            type of objects in result list
	 * @param entityIterable Iterable of entities that needs to be mapped
	 * @param outClass       class of result list element. <strong>Note:</strong>
	 *                       {@code outClass} object <ins>must have</ins> default
	 *                       constructor with no arguments
	 * @return list of mapped object with {@code outClass} type.
	 */
	public static <S, D> List<D> mapAll(final Iterable<S> entityIterable, Class<D> outClass) {
		return makeList(entityIterable).stream().map(entity -> map(entity, outClass)).collect(Collectors.toList());
	}

	/**
	 * Maps {@code source} to {@code destination}.
	 *
	 * @param <S>         type of {@code source} object
	 * @param <D>         type of {@code destination} object
	 * @param source      object to map from
	 * @param destination object to map to
	 */
	public static <S, D> D map(final S source, D destination) {
		modelMapper.map(source, destination);
		return destination;
	}

}

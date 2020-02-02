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
	 * <p>
	 * Note: outClass object must have default constructor with no arguments
	 * </p>
	 *
	 * @param <D>      type of result object.
	 * @param <T>      type of source object to map from.
	 * @param entity   entity that needs to be mapped.
	 * @param outClass class of result object.
	 * @return new object of <code>outClass</code> type.
	 */
	public static <D, T> D map(final T entity, Class<D> outClass) {
		return modelMapper.map(entity, outClass);
	}

	/**
	 * <p>
	 * Note: outClass object must have default constructor with no arguments
	 * </p>
	 *
	 * @param entityList list of entities that needs to be mapped
	 * @param outCLass   class of result list element
	 * @param <D>        type of objects in result list
	 * @param <T>        type of entity in <code>entityList</code>
	 * @return list of mapped object with <code><D></code> type.
	 */
	public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
		return entityList.stream().map(entity -> map(entity, outCLass)).collect(Collectors.toList());
	}

	private static <E> List<E> makeList(Iterable<E> iter) {
		List<E> list = new ArrayList<E>();
		for (E item : iter) {
			list.add(item);
		}
		return list;
	}

	public static <D, T> List<D> mapAll(final Iterable<T> entityList, Class<D> outCLass) {
		return makeList(entityList).stream().map(entity -> map(entity, outCLass)).collect(Collectors.toList());
	}

	/**
	 * Maps {@code source} to {@code destination}.
	 *
	 * @param source      object to map from
	 * @param destination object to map to
	 */
	public static <S, D> D map(final S source, D destination) {
		modelMapper.map(source, destination);
		return destination;
	}

}
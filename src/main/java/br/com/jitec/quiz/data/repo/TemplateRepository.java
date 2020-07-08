package br.com.jitec.quiz.data.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jitec.quiz.business.exception.DataNotFoundException;
import br.com.jitec.quiz.data.entity.StatusTemplate;
import br.com.jitec.quiz.data.entity.Template;

@Repository
public interface TemplateRepository extends CrudRepository<Template, Long> {

	Optional<Template> findByUid(String uid);

	default Template findByUidOrException(String uid) {
		return findByUid(uid).orElseThrow(() -> new DataNotFoundException(Template.class));
	}

	@Query("select t from Template t where t.uid = :templateUid and t.status = 1")
	Optional<Template> findActiveByUid(@Param("templateUid") String templateUid);
	
	@Query("select t from Template t where t.status = :status")
	Iterable<Template> findByStatus(@Param("status") StatusTemplate status);

}

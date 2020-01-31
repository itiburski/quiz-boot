package br.com.jitec.quiz.data.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jitec.quiz.data.entity.Template;

@Repository
public interface TemplateRepository extends CrudRepository<Template, Long> {

	Template findByUid(String uid);

	@Query("select t from Template t where t.uid = :templateUid and t.status = 1")
	Template findActiveByUid(@Param("templateUid") String templateUid);
	
}

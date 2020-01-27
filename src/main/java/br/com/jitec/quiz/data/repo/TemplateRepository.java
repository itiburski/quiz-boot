package br.com.jitec.quiz.data.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.jitec.quiz.data.entity.Template;

@Repository
public interface TemplateRepository extends CrudRepository<Template, Long> {

	Template findByUid(String uid);
	
}

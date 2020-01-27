package br.com.jitec.quiz.data.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.jitec.quiz.data.entity.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

	Question findByUid(String uid);

}

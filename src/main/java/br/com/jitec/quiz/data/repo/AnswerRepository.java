package br.com.jitec.quiz.data.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.jitec.quiz.data.entity.Answer;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {

}

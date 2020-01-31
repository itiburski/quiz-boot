package br.com.jitec.quiz.data.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.jitec.quiz.data.entity.Quiz;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {

	Quiz findByQuizUid(String quizUid);

}

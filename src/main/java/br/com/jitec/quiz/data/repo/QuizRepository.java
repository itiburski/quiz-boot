package br.com.jitec.quiz.data.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jitec.quiz.business.exception.DataNotFoundException;
import br.com.jitec.quiz.data.entity.Quiz;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {

	Optional<Quiz> findByQuizUid(String quizUid);

	default Quiz findByQuizUidOrException(String quizUid) {
		return findByQuizUid(quizUid).orElseThrow(() -> new DataNotFoundException("Quiz"));
	}

	@Query(value = "select q.description, q.uid, ac.choice, count(1) from answers a join answer_choices ac on a.id = ac.answer_id join questions q on q.id = ac.question_id where quiz_id = :quizId group by  q.description, q.uid, ac.choice", nativeQuery = true)
	List<Object[]> findAnswersByQuizId(@Param("quizId") Long quizId);

}
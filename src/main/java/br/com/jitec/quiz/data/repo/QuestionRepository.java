package br.com.jitec.quiz.data.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.jitec.quiz.business.exception.DataNotFoundException;
import br.com.jitec.quiz.data.entity.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

	Optional<Question> findByUid(String uid);

	default Question findByUidOrException(String uid) {
		return findByUid(uid).orElseThrow(() -> new DataNotFoundException("Question"));
	}

}

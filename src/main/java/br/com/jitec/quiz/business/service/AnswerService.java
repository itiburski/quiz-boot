package br.com.jitec.quiz.business.service;

import br.com.jitec.quiz.business.dto.AnswerDto;

public interface AnswerService {

	void saveAnswer(String quizUid, AnswerDto answerDto);
	
}

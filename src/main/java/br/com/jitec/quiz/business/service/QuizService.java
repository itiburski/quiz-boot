package br.com.jitec.quiz.business.service;

import java.util.List;

import br.com.jitec.quiz.business.dto.QuizCompleteDto;
import br.com.jitec.quiz.business.dto.QuizDto;
import br.com.jitec.quiz.business.dto.QuizSummaryDto;

public interface QuizService {

	List<QuizDto> getQuizzes();

	List<QuizDto> getActiveQuizzes();

	QuizDto saveQuiz(String templateUid, QuizDto quizDto);

	QuizDto startQuiz(String quizUid);

	QuizDto endQuiz(String quizUid);

	QuizDto getQuiz(String quizUid);

	QuizCompleteDto getQuizComplete(String quizUid);

	QuizDto updateQuiz(String quizUid, QuizDto quizDto);

	void deleteQuiz(String quizUid);

	QuizSummaryDto getSummary(String quizUid);

}

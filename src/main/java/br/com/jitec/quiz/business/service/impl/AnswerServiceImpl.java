package br.com.jitec.quiz.business.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jitec.quiz.business.dto.AnswerChoiceDto;
import br.com.jitec.quiz.business.dto.AnswerDto;
import br.com.jitec.quiz.business.exception.BusinessValidationException;
import br.com.jitec.quiz.business.precondition.BusinessPreconditions;
import br.com.jitec.quiz.business.service.AnswerService;
import br.com.jitec.quiz.data.entity.Answer;
import br.com.jitec.quiz.data.entity.AnswerChoice;
import br.com.jitec.quiz.data.entity.AnswerChoiceId;
import br.com.jitec.quiz.data.entity.Choices;
import br.com.jitec.quiz.data.entity.Question;
import br.com.jitec.quiz.data.entity.Quiz;
import br.com.jitec.quiz.data.entity.StatusQuiz;
import br.com.jitec.quiz.data.repo.AnswerRepository;
import br.com.jitec.quiz.data.repo.QuestionRepository;
import br.com.jitec.quiz.data.repo.QuizRepository;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Override
	@Transactional
	public void saveAnswer(String quizUid, AnswerDto answerDto) {
		Quiz quiz = BusinessPreconditions.checkFound(quizRepository.findByQuizUid(quizUid), "Quiz");
		
		checkQuizAcceptAnswers(quiz);
		
		Answer answer = new Answer();
		answer.setDate(LocalDateTime.now());
		answer.setQuiz(quiz);
		answer.setAnswerChoices(new ArrayList<>());
		
		answer = answerRepository.save(answer);

		for (AnswerChoiceDto acDto : answerDto.getAnswerChoices()) {
			Question question = BusinessPreconditions.checkFound(questionRepository.findByUid(acDto.getQuestionUid()),
					"Question");

			AnswerChoice answerChoice = new AnswerChoice();
			answerChoice.setId(new AnswerChoiceId(question.getId(), answer.getId()));
			answerChoice.setChoice(getChoice(acDto.getChoice()));

			answer.getAnswerChoices().add(answerChoice);
		}

		answerRepository.save(answer);
	}

	private Choices getChoice(String choice) {
		try {
			return Choices.valueOf(choice);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid choice: " + choice);
		}
	}

	private void checkQuizAcceptAnswers(Quiz quiz) {
		boolean quizActive = StatusQuiz.ACTIVE.equals(quiz.getStatus());
		LocalDateTime now = LocalDateTime.now();
		boolean quizOnTime = now.isAfter(quiz.getBegin()) && now.isBefore(quiz.getEnd());
		if (!quizActive || !quizOnTime) {
			throw new BusinessValidationException("Quiz is no longer accepting answers");
		}
	}

}

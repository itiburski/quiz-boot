package br.com.jitec.quiz.business.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jitec.quiz.business.dto.QuizDto;
import br.com.jitec.quiz.business.exception.BusinessValidationException;
import br.com.jitec.quiz.business.mapper.ObjectMapper;
import br.com.jitec.quiz.business.precondition.BusinessPreconditions;
import br.com.jitec.quiz.business.service.QuizService;
import br.com.jitec.quiz.data.entity.Quiz;
import br.com.jitec.quiz.data.entity.StatusQuiz;
import br.com.jitec.quiz.data.entity.Template;
import br.com.jitec.quiz.data.repo.QuizRepository;
import br.com.jitec.quiz.data.repo.TemplateRepository;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private TemplateRepository templateRepository;

	@Override
	public List<QuizDto> getQuizzes() {
		Iterable<Quiz> quizzes = quizRepository.findAll();

		List<QuizDto> returnQuizzes = ObjectMapper.mapAll(quizzes, QuizDto.class);
		return returnQuizzes;
	}

	@Override
	public QuizDto saveQuiz(String templateUid, QuizDto quizDto) {
		Template template = templateRepository.findActiveByUid(templateUid);
		checkTemplateActive(template);

		Quiz quiz = ObjectMapper.map(quizDto, Quiz.class);
		quiz.setQuizUid(UUID.randomUUID().toString());
		quiz.setStatus(StatusQuiz.PENDING);
		quiz.setTemplate(template);

		Quiz savedQuiz = quizRepository.save(quiz);
		
		QuizDto returnQuiz = ObjectMapper.map(savedQuiz, QuizDto.class);
		return returnQuiz;
	}

	@Override
	public QuizDto startQuiz(String quizUid) {
		Quiz quiz = BusinessPreconditions.checkFound(quizRepository.findByQuizUid(quizUid), "Quiz");
		StatusQuiz newStatus = StatusQuiz.ACTIVE;
		checkStatusChange(quiz, StatusQuiz.PENDING, newStatus);
		quiz.setStatus(newStatus);

		Quiz savedQuiz = quizRepository.save(quiz);

		return ObjectMapper.map(savedQuiz, QuizDto.class);
	}

	@Override
	public QuizDto endQuiz(String quizUid) {
		Quiz quiz = BusinessPreconditions.checkFound(quizRepository.findByQuizUid(quizUid), "Quiz");
		StatusQuiz newStatus = StatusQuiz.ENDED;
		checkStatusChange(quiz, StatusQuiz.ACTIVE, newStatus);
		quiz.setStatus(newStatus);

		Quiz savedQuiz = quizRepository.save(quiz);

		return ObjectMapper.map(savedQuiz, QuizDto.class);
	}

	@Override
	public QuizDto getQuiz(String quizUid) {
		Quiz quiz = BusinessPreconditions.checkFound(quizRepository.findByQuizUid(quizUid), "Quiz");
		return ObjectMapper.map(quiz, QuizDto.class);
	}

	@Override
	public QuizDto updateQuiz(String quizUid, QuizDto quizDto) {

		Quiz quiz = BusinessPreconditions.checkFound(quizRepository.findByQuizUid(quizUid), "Quiz");
		checkNotEndedQuiz(quiz);

		quiz.setDescription(quizDto.getDescription());
		quiz.setBegin(quizDto.getBegin());
		quiz.setEnd(quizDto.getEnd());

		Quiz updatedQuiz = quizRepository.save(quiz);

		return ObjectMapper.map(updatedQuiz, QuizDto.class);
	}

	@Override
	public void deleteQuiz(String quizUid) {
		Quiz quiz = BusinessPreconditions.checkFound(quizRepository.findByQuizUid(quizUid), "Quiz");
		checkNotEndedQuiz(quiz);

		quizRepository.delete(quiz);
	}

	private void checkTemplateActive(Template template) {
		if (template == null) {
			throw new BusinessValidationException("Template is not active");
		}
	}

	private void checkStatusChange(Quiz quiz, StatusQuiz desiredStatus, StatusQuiz nextStatus) {
		if (!desiredStatus.equals(quiz.getStatus())) {
			throw new BusinessValidationException(
					"Not allowed to change quiz status from " + quiz.getStatus() + " to " + nextStatus);
		}
	}

	private void checkNotEndedQuiz(Quiz quiz) {
		if (StatusQuiz.ENDED.equals(quiz.getStatus())) {
			throw new BusinessValidationException("Not allowed to update/delete an ENDED quiz");
		}
	}

}

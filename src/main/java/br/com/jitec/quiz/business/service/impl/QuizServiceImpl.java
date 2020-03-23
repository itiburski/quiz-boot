package br.com.jitec.quiz.business.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jitec.quiz.business.dto.ChoiceDto;
import br.com.jitec.quiz.business.dto.ChoiceSummaryDto;
import br.com.jitec.quiz.business.dto.QuestionDto;
import br.com.jitec.quiz.business.dto.QuestionSummaryDto;
import br.com.jitec.quiz.business.dto.QuizCompleteDto;
import br.com.jitec.quiz.business.dto.QuizDto;
import br.com.jitec.quiz.business.dto.QuizSummaryDto;
import br.com.jitec.quiz.business.exception.BusinessValidationException;
import br.com.jitec.quiz.business.mapper.ObjectMapper;
import br.com.jitec.quiz.business.service.QuizService;
import br.com.jitec.quiz.data.entity.Choices;
import br.com.jitec.quiz.data.entity.Question;
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
		Template template = templateRepository.findActiveByUid(templateUid).orElseThrow(
				() -> new BusinessValidationException(
						"Template is not active. Not allowed to create a Quiz based on it."));

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
		Quiz quiz = quizRepository.findByQuizUidOrException(quizUid);
		StatusQuiz newStatus = StatusQuiz.ACTIVE;
		checkStatusChange(quiz, StatusQuiz.PENDING, newStatus);
		quiz.setStatus(newStatus);

		Quiz savedQuiz = quizRepository.save(quiz);

		return ObjectMapper.map(savedQuiz, QuizDto.class);
	}

	@Override
	public QuizDto endQuiz(String quizUid) {
		Quiz quiz = quizRepository.findByQuizUidOrException(quizUid);
		StatusQuiz newStatus = StatusQuiz.ENDED;
		checkStatusChange(quiz, StatusQuiz.ACTIVE, newStatus);
		quiz.setStatus(newStatus);

		Quiz savedQuiz = quizRepository.save(quiz);

		return ObjectMapper.map(savedQuiz, QuizDto.class);
	}

	@Override
	public QuizDto getQuiz(String quizUid) {
		Quiz quiz = quizRepository.findByQuizUidOrException(quizUid);
		return ObjectMapper.map(quiz, QuizDto.class);
	}

	@Override
	public QuizCompleteDto getQuizComplete(String quizUid) {
		Quiz quiz = quizRepository.findByQuizUidOrException(quizUid);
		QuizCompleteDto quizDetailDto = ObjectMapper.map(quiz, QuizCompleteDto.class);
		quizDetailDto.setQuestions(new ArrayList<>());
		quizDetailDto.setChoices(new ArrayList<>());
		
		for (Question question : quiz.getTemplate().getQuestions()) {
			quizDetailDto.getQuestions().add(ObjectMapper.map(question, QuestionDto.class));
		}

		for (Choices choice : Choices.values()) {
			quizDetailDto.getChoices().add(new ChoiceDto(choice.toString(), choice.getDescription()));
		}

		return quizDetailDto;
	}

	@Override
	public QuizDto updateQuiz(String quizUid, QuizDto quizDto) {
		Quiz quiz = quizRepository.findByQuizUidOrException(quizUid);
		checkPendingQuiz(quiz);

		quiz.setDescription(quizDto.getDescription());
		quiz.setBegin(quizDto.getBegin());
		quiz.setEnd(quizDto.getEnd());

		Quiz updatedQuiz = quizRepository.save(quiz);

		return ObjectMapper.map(updatedQuiz, QuizDto.class);
	}

	@Override
	public void deleteQuiz(String quizUid) {
		Quiz quiz = quizRepository.findByQuizUidOrException(quizUid);
		checkPendingQuiz(quiz);

		quizRepository.delete(quiz);
	}

	@Override
	public QuizSummaryDto getSummary(String quizUid) {

		Quiz quiz = quizRepository.findByQuizUidOrException(quizUid);
		checkEndedQuiz(quiz);

		List<Object[]> answers = quizRepository.findAnswersByQuizId(quiz.getId());

		QuizSummaryDto quizSummary = new QuizSummaryDto();
		quizSummary.setQuizUid(quiz.getQuizUid());
		quizSummary.setDescription(quiz.getDescription());
		quizSummary.setStatus(quiz.getStatus().toString());
		quizSummary.setQuestionsSummary(buildQuestionsSummary(quiz, answers));

		return quizSummary;
	}

	private List<QuestionSummaryDto> buildQuestionsSummary(Quiz quiz, List<Object[]> answers) {
		List<QuestionSummaryDto> questionsSummary = new ArrayList<>();
		String lastQuestionUid = null;
		QuestionSummaryDto qs = null;

		for (Object[] answer : answers) {
			String questionUid = (String) answer[1];
			if (!questionUid.equals(lastQuestionUid)) {
				if (qs != null) {
					questionsSummary.add(qs);
				}

				qs = new QuestionSummaryDto();
				qs.setDescription((String) answer[0]);
				qs.setQuestionUid(questionUid);
				qs.setChoicesSummary(new ArrayList<>());

				lastQuestionUid = questionUid;
			}

			ChoiceSummaryDto choice = new ChoiceSummaryDto();
			choice.setChoice(Choices.valueOf(((Integer) answer[2])).toString());
			choice.setQuantity(((BigInteger) answer[3]).intValue());
			qs.getChoicesSummary().add(choice);
		}

		if (qs != null) {
			questionsSummary.add(qs);
		}

		for (QuestionSummaryDto question : questionsSummary) {
			for (Choices enumChoice : Choices.values()) {
				if (question.getChoicesSummary().stream()
						.noneMatch(cs -> cs.getChoice().equals(enumChoice.toString()))) {
					ChoiceSummaryDto newChoice = new ChoiceSummaryDto(enumChoice.toString(), 0);
					question.getChoicesSummary().add(newChoice);
				}
			}
			Collections.sort(question.getChoicesSummary());
		}

		return questionsSummary;
	}

	private void checkStatusChange(Quiz quiz, StatusQuiz desiredStatus, StatusQuiz nextStatus) {
		if (!desiredStatus.equals(quiz.getStatus())) {
			throw new BusinessValidationException(
					"Not allowed to change quiz status from " + quiz.getStatus() + " to " + nextStatus);
		}
	}

	private void checkPendingQuiz(Quiz quiz) {
		if (!StatusQuiz.PENDING.equals(quiz.getStatus())) {
			throw new BusinessValidationException("Quiz is not PENDING. Not allowed to modify it.");
		}
	}

	private void checkEndedQuiz(Quiz quiz) {
		if (!StatusQuiz.ENDED.equals(quiz.getStatus())) {
			throw new BusinessValidationException("Quiz is not ENDED. It is not possible to get the summary.");
		}
	}

}

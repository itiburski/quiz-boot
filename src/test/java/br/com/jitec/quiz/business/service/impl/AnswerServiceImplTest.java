package br.com.jitec.quiz.business.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.jitec.quiz.business.dto.AnswerChoiceDto;
import br.com.jitec.quiz.business.dto.AnswerDto;
import br.com.jitec.quiz.business.exception.BusinessValidationException;
import br.com.jitec.quiz.business.exception.DataNotFoundException;
import br.com.jitec.quiz.data.entity.Answer;
import br.com.jitec.quiz.data.entity.Question;
import br.com.jitec.quiz.data.entity.Quiz;
import br.com.jitec.quiz.data.entity.StatusQuiz;
import br.com.jitec.quiz.data.repo.AnswerRepository;
import br.com.jitec.quiz.data.repo.QuestionRepository;
import br.com.jitec.quiz.data.repo.QuizRepository;

class AnswerServiceImplTest {

	@InjectMocks
	private AnswerServiceImpl answerService;

	@Mock
	private QuizRepository quizRepository;

	@Mock
	private QuestionRepository questionRepository;

	@Mock
	private AnswerRepository answerRepository;

	private LocalDateTime dtYesterday;
	private LocalDateTime dtTomorrow;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		dtYesterday = LocalDateTime.now().minusDays(1);
		dtTomorrow = LocalDateTime.now().plusDays(1);
	}

	@Test
	void testSaveAnswer() {
		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withStatus(StatusQuiz.ACTIVE).withBegin(dtYesterday)
				.withEnd(dtTomorrow).build();
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(quiz);

		Answer answer = new Answer.Builder().withId(2L).withQuiz(quiz).withDate(LocalDateTime.now())
				.withAnswerChoices(new ArrayList<>()).build();
		Mockito.when(answerRepository.save(Mockito.any(Answer.class))).thenReturn(answer);

		Question question1 = new Question.Builder().withId(1L).withUid("quest-uid-1").withDescription("description-1")
				.build();
		Mockito.when(questionRepository.findByUid("quest-uid-1")).thenReturn(question1);
		Question question2 = new Question.Builder().withId(2L).withUid("quest-uid-2").withDescription("description-2")
				.build();
		Mockito.when(questionRepository.findByUid("quest-uid-2")).thenReturn(question2);
		

		List<AnswerChoiceDto> answerChoicesDto = new ArrayList<>();
		answerChoicesDto.add(new AnswerChoiceDto.Builder().withQuestionUid("quest-uid-1").withChoice("POOR").build());
		answerChoicesDto.add(new AnswerChoiceDto.Builder().withQuestionUid("quest-uid-2").withChoice("GOOD").build());
		AnswerDto answerDto = new AnswerDto.Builder().withAnswerChoices(answerChoicesDto).build();

		answerService.saveAnswer("quiz-uid", answerDto);

		Mockito.verify(answerRepository, Mockito.times(2)).save(Mockito.any(Answer.class));
	}

	@Test
	void testSaveAnswer_WithQuizUidNotFound() {
		Mockito.when(quizRepository.findByQuizUid("unexistent-quiz-uid")).thenReturn(null);

		AnswerDto answerDto = new AnswerDto.Builder().build();

		Assertions.assertThrows(DataNotFoundException.class,
				() -> answerService.saveAnswer("unexistent-uid", answerDto));

		Mockito.verify(answerRepository, Mockito.never()).save(Mockito.any(Answer.class));
	}

	@Test
	void testSaveAnswer_WithIncompatibleCurrentStatus() {
		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withStatus(StatusQuiz.PENDING).withBegin(dtYesterday)
				.withEnd(dtTomorrow).build();
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(quiz);

		AnswerDto answerDto = new AnswerDto.Builder().build();

		Assertions.assertThrows(BusinessValidationException.class,
				() -> answerService.saveAnswer("quiz-uid", answerDto));

		Mockito.verify(answerRepository, Mockito.never()).save(Mockito.any(Answer.class));
	}

	@Test
	void testSaveAnswer_OutOfBeginEndDate() {
		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withStatus(StatusQuiz.ACTIVE).withBegin(dtYesterday)
				.withEnd(dtYesterday).build();
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(quiz);

		AnswerDto answerDto = new AnswerDto.Builder().build();

		Assertions.assertThrows(BusinessValidationException.class,
				() -> answerService.saveAnswer("quiz-uid", answerDto));

		Mockito.verify(answerRepository, Mockito.never()).save(Mockito.any(Answer.class));
	}

	@Test
	void testSaveAnswer_WithInvalidChoice() {
		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withStatus(StatusQuiz.ACTIVE).withBegin(dtYesterday)
				.withEnd(dtTomorrow).build();
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(quiz);

		Answer answer = new Answer.Builder().withId(2L).withQuiz(quiz).withDate(LocalDateTime.now())
				.withAnswerChoices(new ArrayList<>()).build();
		Mockito.when(answerRepository.save(Mockito.any(Answer.class))).thenReturn(answer);

		Question question1 = new Question.Builder().withId(1L).withUid("quest-uid-1").withDescription("description-1")
				.build();
		Mockito.when(questionRepository.findByUid("quest-uid-1")).thenReturn(question1);
		Question question2 = new Question.Builder().withId(2L).withUid("quest-uid-2").withDescription("description-2")
				.build();
		Mockito.when(questionRepository.findByUid("quest-uid-2")).thenReturn(question2);

		List<AnswerChoiceDto> answerChoicesDto = new ArrayList<>();
		answerChoicesDto
				.add(new AnswerChoiceDto.Builder().withQuestionUid("quest-uid-1").withChoice("UNEXISTENT").build());
		answerChoicesDto.add(new AnswerChoiceDto.Builder().withQuestionUid("quest-uid-2").withChoice("GOOD").build());
		AnswerDto answerDto = new AnswerDto.Builder().withAnswerChoices(answerChoicesDto).build();

		Assertions.assertThrows(IllegalArgumentException.class, () -> answerService.saveAnswer("quiz-uid", answerDto));

		Mockito.verify(answerRepository, Mockito.times(1)).save(Mockito.any(Answer.class));
	}

}

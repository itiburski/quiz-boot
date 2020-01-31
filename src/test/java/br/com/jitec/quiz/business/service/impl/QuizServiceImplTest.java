package br.com.jitec.quiz.business.service.impl;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.jitec.quiz.business.dto.QuizDto;
import br.com.jitec.quiz.business.exception.BusinessValidationException;
import br.com.jitec.quiz.business.exception.DataNotFoundException;
import br.com.jitec.quiz.data.entity.Quiz;
import br.com.jitec.quiz.data.entity.StatusQuiz;
import br.com.jitec.quiz.data.entity.StatusTemplate;
import br.com.jitec.quiz.data.entity.Template;
import br.com.jitec.quiz.data.repo.QuizRepository;
import br.com.jitec.quiz.data.repo.TemplateRepository;

class QuizServiceImplTest {

	@InjectMocks
	private QuizServiceImpl quizService;

	@Mock
	private QuizRepository quizRepository;

	@Mock
	private TemplateRepository templateRepository;

	private LocalDateTime dtBegin;
	private LocalDateTime dtEnd;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		dtBegin = LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0);
		dtEnd = LocalDateTime.of(2020, Month.FEBRUARY, 5, 23, 59);
	}

	@Test
	void testGetQuizzes() {
		List<Quiz> quizzes = new ArrayList<>();
		quizzes.add(new Quiz.Builder().withDescription("desc-1").withQuizUid("quiz-uid-1").withBegin(dtBegin)
				.withEnd(dtEnd).withStatus(StatusQuiz.PENDING).build());
		quizzes.add(new Quiz.Builder().withDescription("desc-2").withQuizUid("quiz-uid-2").withBegin(dtBegin)
				.withEnd(dtEnd).withStatus(StatusQuiz.PENDING).build());
		Mockito.when(quizRepository.findAll()).thenReturn(quizzes);

		List<QuizDto> result = quizService.getQuizzes();

		Assertions.assertNotNull(result);
		Assertions.assertEquals(2, result.size());

		Assertions.assertEquals("desc-1", result.get(0).getDescription());
		Assertions.assertEquals("quiz-uid-1", result.get(0).getQuizUid());
		Assertions.assertEquals(StatusQuiz.PENDING.toString(), result.get(0).getStatus());
		Assertions.assertEquals(dtBegin, result.get(0).getBegin());
		Assertions.assertEquals(dtEnd, result.get(0).getEnd());

		Assertions.assertEquals("desc-2", result.get(1).getDescription());
		Assertions.assertEquals("quiz-uid-2", result.get(1).getQuizUid());
		Assertions.assertEquals(StatusQuiz.PENDING.toString(), result.get(1).getStatus());
		Assertions.assertEquals(dtBegin, result.get(1).getBegin());
		Assertions.assertEquals(dtEnd, result.get(1).getEnd());
	}

	@Test
	void testSaveQuiz() {
		Template template = new Template.Builder().withUid("template-uid").withStatus(StatusTemplate.ACTIVE).build();
		Mockito.when(templateRepository.findActiveByUid("template-uid")).thenReturn(template);

		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withDescription("description").withTemplate(template)
				.withStatus(StatusQuiz.PENDING).withBegin(dtBegin).withEnd(dtEnd).build();
		Mockito.when(quizRepository.save(Mockito.any(Quiz.class))).thenReturn(quiz);

		QuizDto quizDto = new QuizDto.Builder().build();
		QuizDto result = quizService.saveQuiz("template-uid", quizDto);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("quiz-uid", result.getQuizUid());
		Assertions.assertEquals("description", result.getDescription());
		Assertions.assertEquals(StatusQuiz.PENDING.toString(), result.getStatus());
		Assertions.assertEquals(dtBegin, result.getBegin());
		Assertions.assertEquals(dtEnd, result.getEnd());
	}

	@Test
	void testSaveQuiz_TemplateNotActive() {
		Mockito.when(templateRepository.findActiveByUid("template-uid")).thenReturn(null);

		QuizDto quizDto = new QuizDto.Builder().build();
		Assertions.assertThrows(BusinessValidationException.class, () -> quizService.saveQuiz("template-uid", quizDto));
	}

	@Test
	void testStartQuiz() {
		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withDescription("description")
				.withStatus(StatusQuiz.PENDING).withBegin(dtBegin).withEnd(dtEnd).build();
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(quiz);
		Mockito.when(quizRepository.save(Mockito.any(Quiz.class))).thenReturn(quiz);

		QuizDto result = quizService.startQuiz("quiz-uid");

		Assertions.assertNotNull(result);
		Assertions.assertEquals("quiz-uid", result.getQuizUid());
		Assertions.assertEquals(StatusQuiz.ACTIVE.toString(), result.getStatus());
	}

	@Test
	void testStartQuiz_WithQuizUidNotFound() {
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(null);

		Assertions.assertThrows(DataNotFoundException.class, () -> quizService.startQuiz("quiz-uid"));
	}

	@Test
	void testStartQuiz_WithIncompatibleCurrentStatus() {
		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withDescription("description")
				.withStatus(StatusQuiz.ACTIVE).withBegin(dtBegin).withEnd(dtEnd).build();
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(quiz);

		Assertions.assertThrows(BusinessValidationException.class, () -> quizService.startQuiz("quiz-uid"));
	}

	@Test
	void testEndQuiz() {
		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withDescription("description")
				.withStatus(StatusQuiz.ACTIVE).withBegin(dtBegin).withEnd(dtEnd).build();
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(quiz);
		Mockito.when(quizRepository.save(Mockito.any(Quiz.class))).thenReturn(quiz);

		QuizDto result = quizService.endQuiz("quiz-uid");

		Assertions.assertNotNull(result);
		Assertions.assertEquals("quiz-uid", result.getQuizUid());
		Assertions.assertEquals(StatusQuiz.ENDED.toString(), result.getStatus());
	}

	@Test
	void testEndQuiz_WithQuizUidNotFound() {
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(null);

		Assertions.assertThrows(DataNotFoundException.class, () -> quizService.endQuiz("quiz-uid"));
	}

	@Test
	void testEndQuiz_WithIncompatibleCurrentStatus() {
		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withDescription("description")
				.withStatus(StatusQuiz.ENDED).withBegin(dtBegin).withEnd(dtEnd).build();
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(quiz);

		Assertions.assertThrows(BusinessValidationException.class, () -> quizService.endQuiz("quiz-uid"));
	}

	@Test
	void testGetQuiz() {
		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withDescription("description")
				.withStatus(StatusQuiz.ACTIVE).withBegin(dtBegin).withEnd(dtEnd).build();
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(quiz);

		QuizDto result = quizService.getQuiz("quiz-uid");

		Assertions.assertNotNull(result);
		Assertions.assertEquals("quiz-uid", result.getQuizUid());
		Assertions.assertEquals("description", result.getDescription());
		Assertions.assertEquals(StatusQuiz.ACTIVE.toString(), result.getStatus());
		Assertions.assertEquals(dtBegin, result.getBegin());
		Assertions.assertEquals(dtEnd, result.getEnd());
	}

	@Test
	void testGetQuiz_WithQuizUidNotFound() {
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(null);

		Assertions.assertThrows(DataNotFoundException.class, () -> quizService.getQuiz("quiz-uid"));
	}

	@Test
	void testUpdateQuiz() {
		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withDescription("description")
				.withStatus(StatusQuiz.ACTIVE).withBegin(dtBegin).withEnd(dtEnd).build();
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(quiz);
		Mockito.when(quizRepository.save(Mockito.any(Quiz.class))).thenReturn(quiz);

		LocalDateTime newBegin = LocalDateTime.of(2020, Month.JANUARY, 01, 0, 0);
		LocalDateTime newEnd = LocalDateTime.of(2020, Month.FEBRUARY, 6, 23, 59);
		QuizDto quizDto = new QuizDto.Builder().withDescription("updated-description").withBegin(newBegin)
				.withEnd(newEnd).build();
		QuizDto result = quizService.updateQuiz("quiz-uid", quizDto);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("quiz-uid", result.getQuizUid());
		Assertions.assertEquals("updated-description", result.getDescription());
		Assertions.assertEquals(StatusQuiz.ACTIVE.toString(), result.getStatus());
		Assertions.assertEquals(newBegin, result.getBegin());
		Assertions.assertEquals(newEnd, result.getEnd());
	}

	@Test
	void testUpdateQuiz_WithQuizUidNotFound() {
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(null);

		QuizDto quizDto = new QuizDto.Builder().build();
		Assertions.assertThrows(DataNotFoundException.class, () -> quizService.updateQuiz("quiz-uid", quizDto));
	}

	@Test
	void testUpdateQuiz_WithIncompatibleCurrentStatus() {
		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withDescription("description")
				.withStatus(StatusQuiz.ENDED).withBegin(dtBegin).withEnd(dtEnd).build();
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(quiz);

		QuizDto quizDto = new QuizDto.Builder().build();
		Assertions.assertThrows(BusinessValidationException.class, () -> quizService.updateQuiz("quiz-uid", quizDto));
	}

	@Test
	void testDeleteQuiz() {
		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withDescription("description")
				.withStatus(StatusQuiz.ACTIVE).withBegin(dtBegin).withEnd(dtEnd).build();
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(quiz);

		quizService.deleteQuiz("quiz-uid");

		Mockito.verify(quizRepository).delete(Mockito.any(Quiz.class));
	}

	@Test
	void testDeleteQuiz_WithQuizUidNotFound() {
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(null);

		Assertions.assertThrows(DataNotFoundException.class, () -> quizService.deleteQuiz("quiz-uid"));
	}

	@Test
	void testDeleteQuiz_WithIncompatibleCurrentStatus() {
		Quiz quiz = new Quiz.Builder().withQuizUid("quiz-uid").withDescription("description")
				.withStatus(StatusQuiz.ENDED).withBegin(dtBegin).withEnd(dtEnd).build();
		Mockito.when(quizRepository.findByQuizUid("quiz-uid")).thenReturn(quiz);

		Assertions.assertThrows(BusinessValidationException.class, () -> quizService.deleteQuiz("quiz-uid"));
	}

}

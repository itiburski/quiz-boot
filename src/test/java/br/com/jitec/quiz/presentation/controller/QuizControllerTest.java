package br.com.jitec.quiz.presentation.controller;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.jitec.quiz.business.dto.QuizCompleteDto;
import br.com.jitec.quiz.business.dto.QuizDto;
import br.com.jitec.quiz.business.dto.QuizSummaryDto;
import br.com.jitec.quiz.business.service.QuizService;
import br.com.jitec.quiz.presentation.payload.QuizCompleteResponse;
import br.com.jitec.quiz.presentation.payload.QuizRequest;
import br.com.jitec.quiz.presentation.payload.QuizResponse;
import br.com.jitec.quiz.presentation.payload.QuizSummaryResponse;

class QuizControllerTest {

	@InjectMocks
	private QuizController quizController;

	@Mock
	private QuizService quizService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetQuizzes() {
		List<QuizDto> quizzesMock = new ArrayList<>();
		ZonedDateTime dtBegin = ZonedDateTime.of(2020, Month.JANUARY.getValue(), 31, 0, 0, 0, 0,
				ZoneId.systemDefault());
		ZonedDateTime dtEnd = ZonedDateTime.of(2020, Month.FEBRUARY.getValue(), 5, 23, 59, 59, 999,
				ZoneId.systemDefault());
		quizzesMock.add(new QuizDto.Builder().withQuizUid("quiz-uid-1").withDescription("desc-1").withBegin(dtBegin)
				.withEnd(dtEnd).withStatus("PENDING").build());
		quizzesMock.add(new QuizDto.Builder().withQuizUid("quiz-uid-2").withDescription("desc-2").withBegin(dtBegin)
				.withEnd(dtEnd).withStatus("PENDING").build());

		Mockito.when(quizService.getQuizzes()).thenReturn(quizzesMock);

		List<QuizResponse> result = quizController.getQuizzes();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals("quiz-uid-1", result.get(0).getQuizUid());
		Assertions.assertEquals("desc-1", result.get(0).getDescription());
		Assertions.assertEquals(dtBegin, result.get(0).getBegin());
		Assertions.assertEquals(dtEnd, result.get(0).getEnd());
		Assertions.assertEquals("PENDING", result.get(0).getStatus());
		Assertions.assertEquals("quiz-uid-2", result.get(1).getQuizUid());
		Assertions.assertEquals("desc-2", result.get(1).getDescription());
		Assertions.assertEquals(dtBegin, result.get(1).getBegin());
		Assertions.assertEquals(dtEnd, result.get(1).getEnd());
		Assertions.assertEquals("PENDING", result.get(1).getStatus());
	}

	@Test
	void testGetActiveQuizzes() {
		List<QuizDto> quizzesMock = new ArrayList<>();
		ZonedDateTime dtBegin = ZonedDateTime.of(2020, Month.JANUARY.getValue(), 31, 0, 0, 0, 0,
				ZoneId.systemDefault());
		ZonedDateTime dtEnd = ZonedDateTime.of(2020, Month.FEBRUARY.getValue(), 5, 23, 59, 59, 999,
				ZoneId.systemDefault());
		quizzesMock.add(new QuizDto.Builder().withQuizUid("quiz-uid-1").withDescription("desc-1").withBegin(dtBegin)
				.withEnd(dtEnd).withStatus("ACTIVE").build());
		quizzesMock.add(new QuizDto.Builder().withQuizUid("quiz-uid-2").withDescription("desc-2").withBegin(dtBegin)
				.withEnd(dtEnd).withStatus("ACTIVE").build());

		Mockito.when(quizService.getActiveQuizzes()).thenReturn(quizzesMock);

		List<QuizResponse> result = quizController.getActiveQuizzes();

		Assertions.assertNotNull(result);
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals("quiz-uid-1", result.get(0).getQuizUid());
		Assertions.assertEquals("desc-1", result.get(0).getDescription());
		Assertions.assertEquals(dtBegin, result.get(0).getBegin());
		Assertions.assertEquals(dtEnd, result.get(0).getEnd());
		Assertions.assertEquals("ACTIVE", result.get(0).getStatus());
		Assertions.assertEquals("quiz-uid-2", result.get(1).getQuizUid());
		Assertions.assertEquals("desc-2", result.get(1).getDescription());
		Assertions.assertEquals(dtBegin, result.get(1).getBegin());
		Assertions.assertEquals(dtEnd, result.get(1).getEnd());
		Assertions.assertEquals("ACTIVE", result.get(1).getStatus());
	}

	@Test
	void testGetQuiz() {
		ZonedDateTime dtBegin = ZonedDateTime.of(2020, Month.JANUARY.getValue(), 31, 0, 0, 0, 0,
				ZoneId.systemDefault());
		ZonedDateTime dtEnd = ZonedDateTime.of(2020, Month.FEBRUARY.getValue(), 5, 23, 59, 59, 999,
				ZoneId.systemDefault());
		QuizDto quizDtoMock = new QuizDto.Builder().withQuizUid("quiz-uid").withDescription("description")
				.withBegin(dtBegin).withEnd(dtEnd).withStatus("PENDING").build();
		Mockito.when(quizService.getQuiz("quiz-uid")).thenReturn(quizDtoMock);

		ResponseEntity<QuizResponse> result = quizController.getQuiz("quiz-uid");

		Assertions.assertNotNull(result);
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals("quiz-uid", result.getBody().getQuizUid());
		Assertions.assertEquals("description", result.getBody().getDescription());
		Assertions.assertEquals(dtBegin, result.getBody().getBegin());
		Assertions.assertEquals(dtEnd, result.getBody().getEnd());
		Assertions.assertEquals("PENDING", result.getBody().getStatus());
	}

	@Test
	void testGetQuizComplete() {
		ZonedDateTime dtBegin = ZonedDateTime.of(2020, Month.JANUARY.getValue(), 31, 0, 0, 0, 0,
				ZoneId.systemDefault());
		ZonedDateTime dtEnd = ZonedDateTime.of(2020, Month.FEBRUARY.getValue(), 5, 23, 59, 59, 999,
				ZoneId.systemDefault());
		QuizCompleteDto quizDtoMock = new QuizCompleteDto.Builder().withQuizUid("quiz-uid").withDescription("description")
				.withBegin(dtBegin).withEnd(dtEnd).withStatus("PENDING").build();
		Mockito.when(quizService.getQuizComplete("quiz-uid")).thenReturn(quizDtoMock);

		ResponseEntity<QuizCompleteResponse> result = quizController.getQuizComplete("quiz-uid");

		Assertions.assertNotNull(result);
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals("quiz-uid", result.getBody().getQuizUid());
		Assertions.assertEquals("description", result.getBody().getDescription());
		Assertions.assertEquals(dtBegin, result.getBody().getBegin());
		Assertions.assertEquals(dtEnd, result.getBody().getEnd());
		Assertions.assertEquals("PENDING", result.getBody().getStatus());
	}

	@Test
	void testPutQuiz() {
		ZonedDateTime dtBegin = ZonedDateTime.of(2020, Month.JANUARY.getValue(), 31, 0, 0, 0, 0,
				ZoneId.systemDefault());
		ZonedDateTime dtEnd = ZonedDateTime.of(2020, Month.FEBRUARY.getValue(), 5, 23, 59, 59, 999,
				ZoneId.systemDefault());
		QuizDto quizDtoMock = new QuizDto.Builder().withQuizUid("quiz-uid").withDescription("description")
				.withBegin(dtBegin).withEnd(dtEnd).withStatus("PENDING").build();
		Mockito.when(quizService.updateQuiz(Mockito.eq("quiz-uid"), Mockito.any(QuizDto.class)))
				.thenReturn(quizDtoMock);

		QuizRequest quizRequest = new QuizRequest.Builder().withDescription("quiz-description").build();
		ResponseEntity<QuizResponse> result = quizController.putQuiz("quiz-uid", quizRequest);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals("quiz-uid", result.getBody().getQuizUid());
		Assertions.assertEquals("description", result.getBody().getDescription());
		Assertions.assertEquals(dtBegin, result.getBody().getBegin());
		Assertions.assertEquals(dtEnd, result.getBody().getEnd());
		Assertions.assertEquals("PENDING", result.getBody().getStatus());
	}

	@Test
	void testDeleteQuiz() {
		ResponseEntity<Void> result = quizController.deleteQuiz("quiz-uid");

		Assertions.assertNotNull(result);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
		Mockito.verify(quizService).deleteQuiz("quiz-uid");
	}

	@Test
	void testStartQuiz() {
		QuizDto quizDtoMock = new QuizDto.Builder().withQuizUid("quiz-uid").build();
		Mockito.when(quizService.startQuiz("quiz-uid")).thenReturn(quizDtoMock);

		ResponseEntity<QuizResponse> result = quizController.startQuiz("quiz-uid");

		Assertions.assertNotNull(result);
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals("quiz-uid", result.getBody().getQuizUid());
	}

	@Test
	void testEndQuiz() {
		QuizDto quizDtoMock = new QuizDto.Builder().withQuizUid("quiz-uid").build();
		Mockito.when(quizService.endQuiz("quiz-uid")).thenReturn(quizDtoMock);

		ResponseEntity<QuizResponse> result = quizController.endQuiz("quiz-uid");

		Assertions.assertNotNull(result);
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals("quiz-uid", result.getBody().getQuizUid());
	}

	@Test
	void testGetSummary() {
		QuizSummaryDto summaryDtoMock = new QuizSummaryDto.Builder().withQuizUid("quiz-uid").build();
		Mockito.when(quizService.getSummary("quiz-uid")).thenReturn(summaryDtoMock);

		ResponseEntity<QuizSummaryResponse> result = quizController.getSummary("quiz-uid");

		Assertions.assertNotNull(result);
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals("quiz-uid", result.getBody().getQuizUid());
	}

}

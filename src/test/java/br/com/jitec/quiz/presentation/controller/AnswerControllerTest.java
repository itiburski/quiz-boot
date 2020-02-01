package br.com.jitec.quiz.presentation.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.jitec.quiz.business.service.AnswerService;
import br.com.jitec.quiz.presentation.payload.AnswerRequest;

class AnswerControllerTest {

	@InjectMocks
	private AnswerController answerController;

	@Mock
	private AnswerService answerService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testPostAnswers() {
		AnswerRequest answerRequest = new AnswerRequest.Builder().build();
		ResponseEntity<Void> result = answerController.postAnswers("quiz-uid", answerRequest);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
	}

}

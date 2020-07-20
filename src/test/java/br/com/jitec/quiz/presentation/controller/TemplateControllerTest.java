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

import br.com.jitec.quiz.business.dto.QuestionDto;
import br.com.jitec.quiz.business.dto.QuizDto;
import br.com.jitec.quiz.business.dto.TemplateDto;
import br.com.jitec.quiz.business.service.QuizService;
import br.com.jitec.quiz.business.service.TemplateService;
import br.com.jitec.quiz.presentation.payload.QuestionRequest;
import br.com.jitec.quiz.presentation.payload.QuestionResponse;
import br.com.jitec.quiz.presentation.payload.QuizRequest;
import br.com.jitec.quiz.presentation.payload.QuizResponse;
import br.com.jitec.quiz.presentation.payload.TemplateCompleteResponse;
import br.com.jitec.quiz.presentation.payload.TemplateRequest;
import br.com.jitec.quiz.presentation.payload.TemplateResponse;

class TemplateControllerTest {

	@InjectMocks
	TemplateController templateController;

	@Mock
	private TemplateService templateService;

	@Mock
	private QuizService quizService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetTemplates() {
		List<QuestionDto> questionsDto = new ArrayList<>();
		questionsDto.add(new QuestionDto.Builder().withUid("question-uid-1").build());
		questionsDto.add(new QuestionDto.Builder().withUid("question-uid-2").build());

		List<TemplateDto> templates = new ArrayList<>();
		templates.add(new TemplateDto.Builder().withDescription("description1").withUid("uid-1")
				.withQuestions(questionsDto).build());
		templates.add(new TemplateDto.Builder().withDescription("description2").withUid("uid-2").build());
		Mockito.when(templateService.getTemplates()).thenReturn(templates);

		List<TemplateResponse> result = templateController.getTemplates();

		Assertions.assertNotNull(result);
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals("uid-1", result.get(0).getTemplateUid());
		Assertions.assertEquals("description1", result.get(0).getDescription());
		Assertions.assertEquals("uid-2", result.get(1).getTemplateUid());
		Assertions.assertEquals("description2", result.get(1).getDescription());
	}

	@Test
	void testGetActiveTemplates() {
		List<QuestionDto> questionsDto = new ArrayList<>();
		questionsDto.add(new QuestionDto.Builder().withUid("question-uid-1").build());
		questionsDto.add(new QuestionDto.Builder().withUid("question-uid-2").build());

		List<TemplateDto> templates = new ArrayList<>();
		templates.add(new TemplateDto.Builder().withDescription("description1").withUid("uid-1").withStatus("ACTIVE")
				.withQuestions(questionsDto).build());
		templates.add(new TemplateDto.Builder().withDescription("description2").withUid("uid-2").withStatus("ACTIVE").build());
		Mockito.when(templateService.getActiveTemplates()).thenReturn(templates);

		List<TemplateResponse> result = templateController.getActiveTemplates();

		Assertions.assertNotNull(result);
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals("uid-1", result.get(0).getTemplateUid());
		Assertions.assertEquals("description1", result.get(0).getDescription());
		Assertions.assertEquals("ACTIVE", result.get(0).getStatus());
		Assertions.assertEquals("uid-2", result.get(1).getTemplateUid());
		Assertions.assertEquals("description2", result.get(1).getDescription());
		Assertions.assertEquals("ACTIVE", result.get(1).getStatus());
	}

	@Test
	void testPostTemplate() {
		TemplateDto savedTemplate = new TemplateDto.Builder().withDescription("description").withUid("template-uid")
				.build();
		Mockito.when(templateService.saveTemplate(Mockito.any(TemplateDto.class))).thenReturn(savedTemplate);

		TemplateRequest request = new TemplateRequest.Builder().withDescription("new-description").build();
		ResponseEntity<TemplateResponse> result = templateController.postTemplate(request);

		Assertions.assertEquals(HttpStatus.CREATED.value(), result.getStatusCodeValue());
		Assertions.assertEquals("description", result.getBody().getDescription());
		Assertions.assertEquals("template-uid", result.getBody().getTemplateUid());
	}

	@Test
	void testActivateTemplate() {
		TemplateDto template = new TemplateDto.Builder().withUid("template-uid").withStatus("ACTIVE").build();
		Mockito.when(templateService.activateTemplate("template-uid")).thenReturn(template);

		ResponseEntity<TemplateResponse> result = templateController.activateTemplate("template-uid");

		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals("ACTIVE", result.getBody().getStatus());
	}

	@Test
	void testInactivateTemplate() {
		TemplateDto template = new TemplateDto.Builder().withUid("template-uid").withStatus("INACTIVE").build();
		Mockito.when(templateService.inactivateTemplate("template-uid")).thenReturn(template);

		ResponseEntity<TemplateResponse> result = templateController.inactivateTemplate("template-uid");

		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals("INACTIVE", result.getBody().getStatus());
	}

	@Test
	void testGetTemplate() {
		TemplateDto template = new TemplateDto.Builder().withDescription("description").withUid("template-uid").build();
		Mockito.when(templateService.getTemplate("template-uid")).thenReturn(template);

		ResponseEntity<TemplateResponse> result = templateController.getTemplate("template-uid");

		Assertions.assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
		Assertions.assertEquals("description", result.getBody().getDescription());
		Assertions.assertEquals("template-uid", result.getBody().getTemplateUid());
	}

	@Test
	void testPutTemplate() {
		TemplateDto template = new TemplateDto.Builder().withDescription("description").withUid("template-uid").build();
		Mockito.when(templateService.updateTemplate(Mockito.eq("template-uid"), Mockito.any(TemplateDto.class)))
				.thenReturn(template);

		TemplateRequest request = new TemplateRequest.Builder().withDescription("new-description").build();
		ResponseEntity<TemplateResponse> result = templateController.putTemplate("template-uid", request);

		Assertions.assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
		Assertions.assertEquals("description", result.getBody().getDescription());
		Assertions.assertEquals("template-uid", result.getBody().getTemplateUid());
	}

	@Test
	void testDeleteTemplate() {
		ResponseEntity<Void> result = templateController.deleteTemplate("template-uid");

		Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatusCodeValue());
		Mockito.verify(templateService).deleteTemplate("template-uid");
	}

	@Test
	void testPostQuiz() {
		ZonedDateTime dtBegin = ZonedDateTime.of(2020, Month.JANUARY.getValue(), 31, 0, 0, 0, 0,
				ZoneId.systemDefault());
		ZonedDateTime dtEnd = ZonedDateTime.of(2020, Month.FEBRUARY.getValue(), 4, 23, 59, 59, 999,
				ZoneId.systemDefault());
		QuizDto quizDtoMock = new QuizDto.Builder().withDescription("description").withQuizUid("quiz-uid")
				.withBegin(dtBegin).withEnd(dtEnd).withStatus("PENDING").build();
		Mockito.when(quizService.saveQuiz(Mockito.eq("template-uid"), Mockito.any(QuizDto.class)))
				.thenReturn(quizDtoMock);

		QuizRequest quizRequest = new QuizRequest.Builder().build();
		ResponseEntity<QuizResponse> result = templateController.postQuiz("template-uid", quizRequest);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
		Assertions.assertEquals("quiz-uid", result.getBody().getQuizUid());
		Assertions.assertEquals("description", result.getBody().getDescription());
		Assertions.assertEquals("PENDING", result.getBody().getStatus());
		Assertions.assertEquals(dtBegin, result.getBody().getBegin());
		Assertions.assertEquals(dtEnd, result.getBody().getEnd());
	}

	@Test
	void testGetTemplateComplete() {
		List<QuestionDto> questionsDto = new ArrayList<>();
		questionsDto.add(new QuestionDto.Builder().withUid("question-uid-1").build());
		questionsDto.add(new QuestionDto.Builder().withUid("question-uid-2").build());

		TemplateDto template = new TemplateDto.Builder().withDescription("description").withUid("template-uid")
				.withQuestions(questionsDto).build();
		Mockito.when(templateService.getTemplate("template-uid")).thenReturn(template);

		ResponseEntity<TemplateCompleteResponse> result = templateController.getTemplateAndQuestions("template-uid");

		Assertions.assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
		Assertions.assertEquals("description", result.getBody().getDescription());
		Assertions.assertEquals("template-uid", result.getBody().getTemplateUid());
		Assertions.assertEquals(2, result.getBody().getQuestions().size());
	}

	@Test
	void testPostQuestion() {
		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-desc").withUid("question-uid")
				.build();
		Mockito.when(templateService.saveQuestion(Mockito.eq("template-uid"), Mockito.any(QuestionDto.class)))
				.thenReturn(questionDto);

		QuestionRequest questionRequest = new QuestionRequest.Builder().withDescription("question-desc").build();
		ResponseEntity<QuestionResponse> result = templateController.postQuestion("template-uid", questionRequest);

		Assertions.assertEquals(HttpStatus.CREATED.value(), result.getStatusCodeValue());
		Assertions.assertEquals("question-desc", result.getBody().getDescription());
		Assertions.assertEquals("question-uid", result.getBody().getQuestionUid());
	}

	@Test
	void testPutQuestion() {
		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-desc").withUid("question-uid")
				.build();
		Mockito.when(templateService.updateQuestion(Mockito.eq("template-uid"), Mockito.eq("question-uid"),
				Mockito.any(QuestionDto.class))).thenReturn(questionDto);

		QuestionRequest questionRequest = new QuestionRequest.Builder().withDescription("question-desc").build();
		ResponseEntity<QuestionResponse> result = templateController.putQuestion("template-uid", "question-uid",
				questionRequest);

		Assertions.assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
		Assertions.assertEquals("question-desc", result.getBody().getDescription());
		Assertions.assertEquals("question-uid", result.getBody().getQuestionUid());
	}

	@Test
	void testDeleteQuestion() {
		ResponseEntity<Void> result = templateController.deleteQuestion("template-uid", "question-uid");

		Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatusCodeValue());
		Mockito.verify(templateService).deleteQuestion("template-uid", "question-uid");
	}

}

package br.com.jitec.quiz.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.jitec.quiz.business.dto.QuestionDto;
import br.com.jitec.quiz.business.dto.TemplateDto;
import br.com.jitec.quiz.business.exception.DataNotFoundException;
import br.com.jitec.quiz.data.entity.Question;
import br.com.jitec.quiz.data.entity.Template;
import br.com.jitec.quiz.data.repo.QuestionRepository;
import br.com.jitec.quiz.data.repo.TemplateRepository;

class TemplateServiceImplTest {

	@InjectMocks
	private TemplateServiceImpl templateService;

	@Mock
	private TemplateRepository templateRepository;

	@Mock
	private QuestionRepository questionRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetAll() {
		List<Template> templates = new ArrayList<>();
		templates.add(new Template.Builder().withUid("template-uid-1").build());
		templates.add(new Template.Builder().withUid("template-uid-2").build());

		Mockito.when(templateRepository.findAll()).thenReturn(templates);

		List<TemplateDto> result = templateService.getTemplates();

		Assertions.assertNotNull(result);
		Assertions.assertEquals(2, result.size());
	}

	@Test
	void testSaveTemplate() {
		Template template = new Template.Builder().withUid("template-uid").build();
		Mockito.when(templateRepository.save(Mockito.any(Template.class))).thenReturn(template);

		TemplateDto templateDto = new TemplateDto.Builder().withDescription("template-description").build();
		TemplateDto result = templateService.saveTemplate(templateDto);

		Assertions.assertNotNull(result);
	}

	@Test
	void testGetTemplate() {
		Template template = new Template.Builder().withUid("template-uid").build();
		Mockito.when(templateRepository.findByUid("template-uid")).thenReturn(template);

		TemplateDto result = templateService.getTemplate("template-uid");

		Assertions.assertNotNull(result);
		Assertions.assertEquals("template-uid", result.getUid());
	}

	@Test
	void testGetTemplateWithTemplateUidNotFound() {
		Mockito.when(templateRepository.findByUid("unexistent-template-uid")).thenReturn(null);

		Assertions.assertThrows(DataNotFoundException.class,
				() -> templateService.getTemplate("unexistent-template-uid"));
	}

	@Test
	void testUpdateTemplate() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("description").build();
		Mockito.when(templateRepository.findByUid("template-uid")).thenReturn(template);
		Mockito.when(templateRepository.save(Mockito.any(Template.class))).thenReturn(template);

		TemplateDto templateDto = new TemplateDto.Builder().withDescription("description-updated").build();
		TemplateDto result = templateService.updateTemplate("template-uid", templateDto);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("template-uid", result.getUid());
		Assertions.assertEquals("description-updated", result.getDescription());
	}

	@Test
	void testUpdateTemplateWithTemplateUidNotFound() {
		Mockito.when(templateRepository.findByUid("unexistent-template-uid")).thenReturn(null);

		Assertions.assertThrows(DataNotFoundException.class, () -> {
			TemplateDto templateDto = new TemplateDto.Builder().withDescription("description-updated").build();
			templateService.updateTemplate("unexistent-template-uid", templateDto);
		});
	}

	@Test
	void testDeleteTemplate() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("description").build();
		Mockito.when(templateRepository.findByUid("template-uid")).thenReturn(template);

		templateService.deleteTemplate("template-uid");

		Mockito.verify(templateRepository).delete(Mockito.any(Template.class));
	}

	@Test
	void testDeleteTemplateWithTemplateUidNotFound() {
		Mockito.when(templateRepository.findByUid("unexistent-template-uid")).thenReturn(null);

		Assertions.assertThrows(DataNotFoundException.class,
				() -> templateService.deleteTemplate("unexistent-template-uid"));
	}

	@Test
	void testSaveQuestion() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("template-description")
				.build();
		Mockito.when(templateRepository.findByUid("template-uid")).thenReturn(template);

		Question question = new Question.Builder().build();
		Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(question);

		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-description").build();
		QuestionDto result = templateService.saveQuestion("template-uid", questionDto);

		Assertions.assertNotNull(result);
	}

	@Test
	void testSaveQuestionWithTemplateUidNotFound() {
		Mockito.when(templateRepository.findByUid("unexistent-template-uid")).thenReturn(null);

		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-dto").build();
		Assertions.assertThrows(DataNotFoundException.class,
				() -> templateService.saveQuestion("unexistent-template-uid", questionDto));
	}

	@Test
	void testUpdateQuestion() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("description").build();
		Question question = new Question.Builder().withDescription("question").withUid("question-uid")
				.withTemplate(template).build();
		Mockito.when(questionRepository.findByUid("question-uid")).thenReturn(question);

		Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(question);

		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-updated").build();
		QuestionDto result = templateService.updateQuestion("template-uid", "question-uid", questionDto);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("question-uid", result.getQuestionUid());
		Assertions.assertEquals("question-updated", result.getDescription());
	}

	@Test
	void testUpdateQuestionWithQuestionUidNotFound() {
		Mockito.when(questionRepository.findByUid("unexistent-question-uid")).thenReturn(null);

		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-updated").build();
		Assertions.assertThrows(DataNotFoundException.class,
				() -> templateService.updateQuestion("template-uid", "unexistent-question-uid", questionDto));
	}

	@Test
	void testUpdateQuestionWithTemplateUidQuestionUidDoNotMatch() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("template-description")
				.build();
		Question question = new Question.Builder().withDescription("question-description").withUid("question-uid")
				.withTemplate(template).build();
		Mockito.when(questionRepository.findByUid("question-uid")).thenReturn(question);

		Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(question);

		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-updated").build();
		Assertions.assertThrows(DataNotFoundException.class,
				() -> templateService.updateQuestion("not-match-template-id", "question-uid", questionDto));
	}

	@Test
	void testDeleteQuestion() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("template-description")
				.build();
		Question question = new Question.Builder().withDescription("question-description").withUid("question-uid")
				.withTemplate(template).build();
		Mockito.when(questionRepository.findByUid("question-uid")).thenReturn(question);

		templateService.deleteQuestion("template-uid", "question-uid");
		
		Mockito.verify(questionRepository).delete(Mockito.any(Question.class));
	}

	@Test
	void testDeleteQuestionWithQuestionUidNotFound() {
		Mockito.when(questionRepository.findByUid("unexistent-question-uid")).thenReturn(null);

		Assertions.assertThrows(DataNotFoundException.class,
				() -> templateService.deleteQuestion("template-uid", "unexistent-question-uid"));
	}

	@Test
	void testDeleteQuestionWithTemplateUidQuestionUidDoNotMatch() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("description").build();
		Question question = new Question.Builder().withDescription("question").withUid("question-uid")
				.withTemplate(template)
				.build();
		Mockito.when(questionRepository.findByUid("question-uid")).thenReturn(question);

		Assertions.assertThrows(DataNotFoundException.class,
				() -> templateService.deleteQuestion("not-match-template-uid", "question-uid"));
	}

}

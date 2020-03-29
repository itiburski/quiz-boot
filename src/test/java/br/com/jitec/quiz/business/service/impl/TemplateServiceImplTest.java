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
import br.com.jitec.quiz.business.exception.BusinessValidationException;
import br.com.jitec.quiz.business.exception.DataNotFoundException;
import br.com.jitec.quiz.data.entity.Question;
import br.com.jitec.quiz.data.entity.StatusTemplate;
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
	void testActivateTemplate() {
		Template template = new Template.Builder().withUid("template-uid").withStatus(StatusTemplate.PENDING).build();
		Mockito.when(templateRepository.findByUidOrException("template-uid")).thenReturn(template);
		Mockito.when(templateRepository.save(Mockito.any(Template.class))).thenReturn(template);

		TemplateDto result = templateService.activateTemplate("template-uid");

		Assertions.assertNotNull(result);
		Assertions.assertEquals("ACTIVE", result.getStatus());
	}

	@Test
	void testActivateTemplate_WithTemplateUidNotFound() {
		Mockito.when(templateRepository.findByUidOrException("template-uid"))
				.thenThrow(new DataNotFoundException(Template.class));

		Assertions.assertThrows(DataNotFoundException.class, () -> templateService.activateTemplate("template-uid"));
	}

	@Test
	void testActivateTemplate_WithIncompatibleCurrentStatus() {
		Template template = new Template.Builder().withUid("template-uid").withStatus(StatusTemplate.ACTIVE).build();
		Mockito.when(templateRepository.findByUidOrException("template-uid")).thenReturn(template);
		Mockito.when(templateRepository.save(Mockito.any(Template.class))).thenReturn(template);

		Assertions.assertThrows(BusinessValidationException.class,
				() -> templateService.activateTemplate("template-uid"));
	}

	@Test
	void testInactivateTemplate() {
		Template template = new Template.Builder().withUid("template-uid").withStatus(StatusTemplate.ACTIVE).build();
		Mockito.when(templateRepository.findByUidOrException("template-uid")).thenReturn(template);
		Mockito.when(templateRepository.save(Mockito.any(Template.class))).thenReturn(template);

		TemplateDto result = templateService.inactivateTemplate("template-uid");

		Assertions.assertNotNull(result);
		Assertions.assertEquals("INACTIVE", result.getStatus());
	}

	@Test
	void testInactivateTemplate_WithTemplateUidNotFound() {
		Mockito.when(templateRepository.findByUidOrException("template-uid"))
				.thenThrow(new DataNotFoundException(Template.class));

		Assertions.assertThrows(DataNotFoundException.class, () -> templateService.inactivateTemplate("template-uid"));
	}

	@Test
	void testInactivateTemplate_WithIncompatibleCurrentStatus() {
		Template template = new Template.Builder().withUid("template-uid").withStatus(StatusTemplate.INACTIVE).build();
		Mockito.when(templateRepository.findByUidOrException("template-uid")).thenReturn(template);
		Mockito.when(templateRepository.save(Mockito.any(Template.class))).thenReturn(template);

		Assertions.assertThrows(BusinessValidationException.class,
				() -> templateService.inactivateTemplate("template-uid"));
	}

	@Test
	void testGetTemplate() {
		Template template = new Template.Builder().withUid("template-uid").build();
		Mockito.when(templateRepository.findByUidOrException("template-uid")).thenReturn(template);

		TemplateDto result = templateService.getTemplate("template-uid");

		Assertions.assertNotNull(result);
		Assertions.assertEquals("template-uid", result.getUid());
	}

	@Test
	void testGetTemplate_WithTemplateUidNotFound() {
		Mockito.when(templateRepository.findByUidOrException("unexistent-template-uid"))
				.thenThrow(new DataNotFoundException(Template.class));

		Assertions.assertThrows(DataNotFoundException.class,
				() -> templateService.getTemplate("unexistent-template-uid"));
	}

	@Test
	void testUpdateTemplate() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("description")
				.withStatus(StatusTemplate.PENDING).build();
		Mockito.when(templateRepository.findByUidOrException("template-uid")).thenReturn(template);
		Mockito.when(templateRepository.save(Mockito.any(Template.class))).thenReturn(template);

		TemplateDto templateDto = new TemplateDto.Builder().withDescription("description-updated").build();
		TemplateDto result = templateService.updateTemplate("template-uid", templateDto);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("template-uid", result.getUid());
		Assertions.assertEquals("description-updated", result.getDescription());
	}

	@Test
	void testUpdateTemplate_WithStatusNotPending() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("description")
				.withStatus(StatusTemplate.ACTIVE).build();
		Mockito.when(templateRepository.findByUidOrException("template-uid")).thenReturn(template);

		TemplateDto templateDto = new TemplateDto.Builder().withDescription("description-updated").build();
		Assertions.assertThrows(BusinessValidationException.class,
				() -> templateService.updateTemplate("template-uid", templateDto));

		Mockito.verify(templateRepository, Mockito.never()).save(Mockito.any(Template.class));
	}

	@Test
	void testUpdateTemplate_WithTemplateUidNotFound() {
		Mockito.when(templateRepository.findByUidOrException("unexistent-template-uid"))
				.thenThrow(new DataNotFoundException(Template.class));

		Assertions.assertThrows(DataNotFoundException.class, () -> {
			TemplateDto templateDto = new TemplateDto.Builder().withDescription("description-updated").build();
			templateService.updateTemplate("unexistent-template-uid", templateDto);
		});

		Mockito.verify(templateRepository, Mockito.never()).save(Mockito.any(Template.class));
	}

	@Test
	void testDeleteTemplate() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("description")
				.withStatus(StatusTemplate.PENDING).build();
		Mockito.when(templateRepository.findByUidOrException("template-uid")).thenReturn(template);

		templateService.deleteTemplate("template-uid");

		Mockito.verify(templateRepository).delete(Mockito.any(Template.class));
	}

	@Test
	void testDeleteTemplate_WithStatusNotPending() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("description")
				.withStatus(StatusTemplate.ACTIVE).build();
		Mockito.when(templateRepository.findByUidOrException("template-uid")).thenReturn(template);

		Assertions.assertThrows(BusinessValidationException.class,
				() -> templateService.deleteTemplate("template-uid"));

		Mockito.verify(templateRepository, Mockito.never()).delete(Mockito.any(Template.class));
	}

	@Test
	void testDeleteTemplate_WithTemplateUidNotFound() {
		Mockito.when(templateRepository.findByUidOrException("unexistent-template-uid"))
				.thenThrow(new DataNotFoundException(Template.class));
		Assertions.assertThrows(DataNotFoundException.class,
				() -> templateService.deleteTemplate("unexistent-template-uid"));

		Mockito.verify(templateRepository, Mockito.never()).delete(Mockito.any(Template.class));
	}

	@Test
	void testSaveQuestion() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("template-description")
				.withStatus(StatusTemplate.PENDING).build();
		Mockito.when(templateRepository.findByUidOrException("template-uid")).thenReturn(template);

		Question question = new Question.Builder().build();
		Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(question);

		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-description").build();
		QuestionDto result = templateService.saveQuestion("template-uid", questionDto);

		Assertions.assertNotNull(result);
	}

	@Test
	void testSaveQuestion_WithStatusNotPending() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("template-description")
				.withStatus(StatusTemplate.ACTIVE).build();
		Mockito.when(templateRepository.findByUidOrException("template-uid")).thenReturn(template);

		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-description").build();
		Assertions.assertThrows(BusinessValidationException.class,
				() -> templateService.saveQuestion("template-uid", questionDto));

		Mockito.verify(questionRepository, Mockito.never()).save(Mockito.any(Question.class));
	}

	@Test
	void testSaveQuestion_WithTemplateUidNotFound() {
		Mockito.when(templateRepository.findByUidOrException("unexistent-template-uid"))
				.thenThrow(new DataNotFoundException(Template.class));

		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-dto").build();
		Assertions.assertThrows(DataNotFoundException.class,
				() -> templateService.saveQuestion("unexistent-template-uid", questionDto));

		Mockito.verify(questionRepository, Mockito.never()).save(Mockito.any(Question.class));
	}

	@Test
	void testUpdateQuestion() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("description")
				.withStatus(StatusTemplate.PENDING).build();
		Question question = new Question.Builder().withDescription("question").withUid("question-uid")
				.withTemplate(template).build();
		Mockito.when(questionRepository.findByUidOrException("question-uid")).thenReturn(question);

		Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(question);

		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-updated").build();
		QuestionDto result = templateService.updateQuestion("template-uid", "question-uid", questionDto);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("question-uid", result.getQuestionUid());
		Assertions.assertEquals("question-updated", result.getDescription());
	}

	@Test
	void testUpdateQuestion_WithStatusNotPending() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("description")
				.withStatus(StatusTemplate.INACTIVE).build();
		Question question = new Question.Builder().withDescription("question").withUid("question-uid")
				.withTemplate(template).build();
		Mockito.when(questionRepository.findByUidOrException("question-uid")).thenReturn(question);

		Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(question);

		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-updated").build();
		Assertions.assertThrows(BusinessValidationException.class,
				() -> templateService.updateQuestion("template-uid", "question-uid", questionDto));

		Mockito.verify(questionRepository, Mockito.never()).save(Mockito.any(Question.class));
	}

	@Test
	void testUpdateQuestion_WithQuestionUidNotFound() {
		Mockito.when(questionRepository.findByUidOrException("unexistent-question-uid"))
				.thenThrow(new DataNotFoundException(Question.class));

		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-updated").build();
		Assertions.assertThrows(DataNotFoundException.class,
				() -> templateService.updateQuestion("template-uid", "unexistent-question-uid", questionDto));

		Mockito.verify(questionRepository, Mockito.never()).save(Mockito.any(Question.class));
	}

	@Test
	void testUpdateQuestion_WithTemplateUidQuestionUidDoNotMatch() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("template-description")
				.withStatus(StatusTemplate.PENDING).build();
		Question question = new Question.Builder().withDescription("question-description").withUid("question-uid")
				.withTemplate(template).build();
		Mockito.when(questionRepository.findByUidOrException("question-uid")).thenReturn(question);

		Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(question);

		QuestionDto questionDto = new QuestionDto.Builder().withDescription("question-updated").build();
		Assertions.assertThrows(BusinessValidationException.class,
				() -> templateService.updateQuestion("not-match-template-id", "question-uid", questionDto));
	}

	@Test
	void testDeleteQuestion() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("template-description")
				.withStatus(StatusTemplate.PENDING).build();
		Question question = new Question.Builder().withDescription("question-description").withUid("question-uid")
				.withTemplate(template).build();
		Mockito.when(questionRepository.findByUidOrException("question-uid")).thenReturn(question);

		templateService.deleteQuestion("template-uid", "question-uid");
		
		Mockito.verify(questionRepository).delete(Mockito.any(Question.class));
	}

	@Test
	void testDeleteQuestion_WithStatusNotPending() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("template-description")
				.withStatus(StatusTemplate.ACTIVE).build();
		Question question = new Question.Builder().withDescription("question-description").withUid("question-uid")
				.withTemplate(template).build();
		Mockito.when(questionRepository.findByUidOrException("question-uid")).thenReturn(question);

		Assertions.assertThrows(BusinessValidationException.class,
				() -> templateService.deleteQuestion("template-uid", "question-uid"));

		Mockito.verify(questionRepository, Mockito.never()).delete(Mockito.any(Question.class));
	}

	@Test
	void testDeleteQuestion_WithQuestionUidNotFound() {
		Mockito.when(questionRepository.findByUidOrException("unexistent-question-uid"))
				.thenThrow(new DataNotFoundException(Question.class));

		Assertions.assertThrows(DataNotFoundException.class,
				() -> templateService.deleteQuestion("template-uid", "unexistent-question-uid"));

		Mockito.verify(questionRepository, Mockito.never()).delete(Mockito.any(Question.class));
	}

	@Test
	void testDeleteQuestion_WithTemplateUidQuestionUidDoNotMatch() {
		Template template = new Template.Builder().withUid("template-uid").withDescription("description")
				.withStatus(StatusTemplate.PENDING).build();
		Question question = new Question.Builder().withDescription("question").withUid("question-uid")
				.withTemplate(template)
				.build();
		Mockito.when(questionRepository.findByUidOrException("question-uid")).thenReturn(question);

		Assertions.assertThrows(BusinessValidationException.class,
				() -> templateService.deleteQuestion("not-match-template-uid", "question-uid"));
	}

}

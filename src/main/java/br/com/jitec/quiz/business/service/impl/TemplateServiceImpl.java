package br.com.jitec.quiz.business.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jitec.quiz.business.dto.QuestionDto;
import br.com.jitec.quiz.business.dto.TemplateDto;
import br.com.jitec.quiz.business.exception.BusinessValidationException;
import br.com.jitec.quiz.business.mapper.ObjectMapper;
import br.com.jitec.quiz.business.service.TemplateService;
import br.com.jitec.quiz.data.entity.Question;
import br.com.jitec.quiz.data.entity.StatusTemplate;
import br.com.jitec.quiz.data.entity.Template;
import br.com.jitec.quiz.data.repo.QuestionRepository;
import br.com.jitec.quiz.data.repo.TemplateRepository;

@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private TemplateRepository templateRepository;
	
	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public List<TemplateDto> getTemplates() {		
		Iterable<Template> templates = templateRepository.findAll();
		
		List<TemplateDto> map = ObjectMapper.mapAll(templates, TemplateDto.class);
		return map;
	}

	@Override
	public List<TemplateDto> getActiveTemplates() {
		Iterable<Template> templates = templateRepository.findByStatus(StatusTemplate.ACTIVE);

		List<TemplateDto> map = ObjectMapper.mapAll(templates, TemplateDto.class);
		return map;
	}

	@Override
	public TemplateDto saveTemplate(TemplateDto templateDto) {
		Template template = ObjectMapper.map(templateDto, Template.class);
		template.setUid(UUID.randomUUID().toString());
		template.setStatus(StatusTemplate.PENDING);
		
		Template savedTemplate = templateRepository.save(template);
		
		TemplateDto returnTemplate = ObjectMapper.map(savedTemplate, TemplateDto.class);
		return returnTemplate;
	}

	@Override
	public TemplateDto activateTemplate(String templateUid) {
		Template template = templateRepository.findByUidOrException(templateUid);
		StatusTemplate newStatus = StatusTemplate.ACTIVE;
		checkStatusChange(template, StatusTemplate.PENDING, newStatus);
		template.setStatus(newStatus);
		Template updatedTemplate = templateRepository.save(template);

		return ObjectMapper.map(updatedTemplate, TemplateDto.class);
	}

	@Override
	public TemplateDto inactivateTemplate(String templateUid) {
		Template template = templateRepository.findByUidOrException(templateUid);
		StatusTemplate newStatus = StatusTemplate.INACTIVE;
		checkStatusChange(template, StatusTemplate.ACTIVE, newStatus);
		template.setStatus(newStatus);
		Template updatedTemplate = templateRepository.save(template);

		return ObjectMapper.map(updatedTemplate, TemplateDto.class);
	}

	@Override
	public TemplateDto getTemplate(String templateUid) {
		Template template = templateRepository.findByUidOrException(templateUid);
		TemplateDto map = ObjectMapper.map(template, TemplateDto.class);
		return map;
	}

	@Override
	public TemplateDto updateTemplate(String templateUid, TemplateDto templateDto) {
		Template template = templateRepository.findByUidOrException(templateUid);
		checkTemplatePending(template);

		template.setDescription(templateDto.getDescription());
		Template updatedTemplate = templateRepository.save(template);
		return ObjectMapper.map(updatedTemplate, TemplateDto.class);
	}

	@Override
	public void deleteTemplate(String templateUid) {
		Template template = templateRepository.findByUidOrException(templateUid);
		checkTemplatePending(template);

		templateRepository.delete(template);
	}

	@Override
	public QuestionDto saveQuestion(String templateUid, QuestionDto questionDto) {
		Template template = templateRepository.findByUidOrException(templateUid);
		checkTemplatePending(template);

		Question question = ObjectMapper.map(questionDto, Question.class);
		question.setTemplate(template);
		question.setUid(UUID.randomUUID().toString());
		
		Question savedQuestion = questionRepository.save(question);
		
		return ObjectMapper.map(savedQuestion, QuestionDto.class);
	}

	@Override
	public QuestionDto updateQuestion(String templateUid, String questionUid, QuestionDto questionDto) {
		Question question = questionRepository.findByUidOrException(questionUid);
		checkTemplatePending(question.getTemplate());
		checkMatchTemplateUidQuestionUid(templateUid, question);
		
		question.setDescription(questionDto.getDescription());
		Question updatedQuestion = questionRepository.save(question);

		return ObjectMapper.map(updatedQuestion, QuestionDto.class);
	}

	@Override
	public void deleteQuestion(String templateUid, String questionUid) {
		Question question = questionRepository.findByUidOrException(questionUid);
		checkTemplatePending(question.getTemplate());
		checkMatchTemplateUidQuestionUid(templateUid, question);

		questionRepository.delete(question);
	}

	private void checkMatchTemplateUidQuestionUid(String templateUid, Question question) {
		if (!question.getTemplate().getUid().equals(templateUid)) {
			throw new BusinessValidationException("The questionUid does not belong to the templateUid");
		}
	}

	private void checkStatusChange(Template template, StatusTemplate desiredStatus, StatusTemplate nextStatus) {
		if (!desiredStatus.equals(template.getStatus())) {
			throw new BusinessValidationException(
					"Not allowed to change template status from " + template.getStatus() + " to " + nextStatus);
		}
	}

	private void checkTemplatePending(Template template) {
		if (!StatusTemplate.PENDING.equals(template.getStatus())) {
			throw new BusinessValidationException("Template is not PENDING. Not allowed to modify it.");
		}
	}

}

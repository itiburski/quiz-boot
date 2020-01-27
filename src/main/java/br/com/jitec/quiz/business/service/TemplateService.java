package br.com.jitec.quiz.business.service;

import java.util.List;

import br.com.jitec.quiz.business.dto.QuestionDto;
import br.com.jitec.quiz.business.dto.TemplateDto;

public interface TemplateService {

	List<TemplateDto> getTemplates();

	TemplateDto saveTemplate(TemplateDto templateDto);

	TemplateDto getTemplate(String uid);

	TemplateDto updateTemplate(String uid, TemplateDto templateDto);

	void deleteTemplate(String uid);

	QuestionDto saveQuestion(String templateUid, QuestionDto questionDto);

	QuestionDto updateQuestion(String templateUid, String questionUid, QuestionDto questionDto);

	void deleteQuestion(String templateUid, String questionUid);
	
}

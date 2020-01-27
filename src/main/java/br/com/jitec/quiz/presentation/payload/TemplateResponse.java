package br.com.jitec.quiz.presentation.payload;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class TemplateResponse {

	@ApiModelProperty("Generated Template UID")
	private String templateUid;

	@ApiModelProperty("Template description")
	private String description;

	@ApiModelProperty("All questions related to current template")
	private List<QuestionResponse> questions;

	// status??

	public String getTemplateUid() {
		return templateUid;
	}

	public void setTemplateUid(String templateUid) {
		this.templateUid = templateUid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<QuestionResponse> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionResponse> questions) {
		this.questions = questions;
	}

}

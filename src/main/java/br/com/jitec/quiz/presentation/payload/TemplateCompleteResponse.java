package br.com.jitec.quiz.presentation.payload;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class TemplateCompleteResponse {

	@ApiModelProperty("Generated Template UID")
	private String templateUid;

	@ApiModelProperty("Template description")
	private String description;

	@ApiModelProperty("Template status")
	private String status;

	@ApiModelProperty("All questions related to current template")
	private List<QuestionResponse> questions;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<QuestionResponse> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionResponse> questions) {
		this.questions = questions;
	}

}

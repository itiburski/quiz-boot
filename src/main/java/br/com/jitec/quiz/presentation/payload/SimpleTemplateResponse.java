package br.com.jitec.quiz.presentation.payload;

import io.swagger.annotations.ApiModelProperty;

public class SimpleTemplateResponse {

	@ApiModelProperty("Generated Template UID")
	private String templateUid;

	@ApiModelProperty("Template description")
	private String description;

	@ApiModelProperty("Template status")
	private String status;

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

}

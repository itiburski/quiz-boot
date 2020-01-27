package br.com.jitec.quiz.presentation.payload;

import io.swagger.annotations.ApiModelProperty;

public class TemplateRequest {

	@ApiModelProperty("Template description")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static class Builder {
		private TemplateRequest instance;

		public Builder() {
			instance = new TemplateRequest();
		}

		public Builder withDescription(String description) {
			instance.setDescription(description);
			return this;
		}

		public TemplateRequest build() {
			return instance;
		}
	}

}

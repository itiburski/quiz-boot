package br.com.jitec.quiz.presentation.payload;

import io.swagger.annotations.ApiModelProperty;

public class QuestionRequest {

	@ApiModelProperty("Question description")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static class Builder {
		private QuestionRequest instance;

		public Builder() {
			instance = new QuestionRequest();
		}

		public Builder withDescription(String description) {
			instance.setDescription(description);
			return this;
		}

		public QuestionRequest build() {
			return instance;
		}
	}

}

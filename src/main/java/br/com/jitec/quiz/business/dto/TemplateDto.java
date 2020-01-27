package br.com.jitec.quiz.business.dto;

import java.util.List;

public class TemplateDto {

	private String uid;
	private String description;
	private List<QuestionDto> questions;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<QuestionDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDto> questions) {
		this.questions = questions;
	}

	public static class Builder {
		private TemplateDto instance;

		public Builder() {
			instance = new TemplateDto();
		}

		public Builder withUid(String uid) {
			instance.setUid(uid);
			return this;
		}

		public Builder withDescription(String description) {
			instance.setDescription(description);
			return this;
		}

		public TemplateDto build() {
			return instance;
		}
	}

}

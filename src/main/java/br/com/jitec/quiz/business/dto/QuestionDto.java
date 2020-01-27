package br.com.jitec.quiz.business.dto;

public class QuestionDto {

	private String questionUid;
	private String description;

	public String getQuestionUid() {
		return questionUid;
	}

	public void setQuestionUid(String questionUid) {
		this.questionUid = questionUid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static class Builder {
		private QuestionDto instance;

		public Builder() {
			instance = new QuestionDto();
		}

		public Builder withUid(String uid) {
			instance.setQuestionUid(uid);
			return this;
		}

		public Builder withDescription(String description) {
			instance.setDescription(description);
			return this;
		}

		public QuestionDto build() {
			return instance;
		}
	}

}

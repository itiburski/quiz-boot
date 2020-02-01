package br.com.jitec.quiz.business.dto;

public class AnswerChoiceDto {

	private String questionUid;
	private String choice;

	public String getQuestionUid() {
		return questionUid;
	}

	public void setQuestionUid(String questionUid) {
		this.questionUid = questionUid;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public static class Builder {
		private AnswerChoiceDto instance;

		public Builder() {
			instance = new AnswerChoiceDto();
		}

		public Builder withQuestionUid(String questionUid) {
			instance.setQuestionUid(questionUid);
			return this;
		}

		public Builder withChoice(String choice) {
			instance.setChoice(choice);
			return this;
		}

		public AnswerChoiceDto build() {
			return instance;
		}
	}

}

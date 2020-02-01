package br.com.jitec.quiz.presentation.payload;

public class AnswerChoiceRequest {

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
		private AnswerChoiceRequest instance;

		public Builder() {
			instance = new AnswerChoiceRequest();
		}

		public Builder withDescription(String questionUid) {
			instance.setQuestionUid(questionUid);
			return this;
		}

		public Builder withChoice(String choice) {
			instance.setChoice(choice);
			return this;
		}

		public AnswerChoiceRequest build() {
			return instance;
		}
	}
}

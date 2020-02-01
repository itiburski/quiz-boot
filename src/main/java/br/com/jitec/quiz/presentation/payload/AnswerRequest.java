package br.com.jitec.quiz.presentation.payload;

import java.util.List;

public class AnswerRequest {

	private List<AnswerChoiceRequest> answerChoices;

	public List<AnswerChoiceRequest> getAnswerChoices() {
		return answerChoices;
	}

	public void setAnswerChoices(List<AnswerChoiceRequest> answerChoices) {
		this.answerChoices = answerChoices;
	}
	
	public static class Builder {
		private AnswerRequest instance;

		public Builder() {
			instance = new AnswerRequest();
		}

		public Builder withAnswerChoices(List<AnswerChoiceRequest> answerChoices) {
			instance.setAnswerChoices(answerChoices);
			return this;
		}

		public AnswerRequest build() {
			return instance;
		}
	}

}

package br.com.jitec.quiz.business.dto;

import java.util.List;

public class AnswerDto {

	private List<AnswerChoiceDto> answerChoices;

	public List<AnswerChoiceDto> getAnswerChoices() {
		return answerChoices;
	}

	public void setAnswerChoices(List<AnswerChoiceDto> answerChoices) {
		this.answerChoices = answerChoices;
	}

	public static class Builder {
		private AnswerDto instance;

		public Builder() {
			instance = new AnswerDto();
		}

		public Builder withAnswerChoices(List<AnswerChoiceDto> answerChoices) {
			instance.setAnswerChoices(answerChoices);
			return this;
		}

		public AnswerDto build() {
			return instance;
		}
	}

}

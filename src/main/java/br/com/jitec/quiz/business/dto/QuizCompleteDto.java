package br.com.jitec.quiz.business.dto;

import java.time.ZonedDateTime;
import java.util.List;

public class QuizCompleteDto {

	private String quizUid;
	private String description;
	private ZonedDateTime begin;
	private ZonedDateTime end;
	private String status;
	private List<QuestionDto> questions;
	private List<ChoiceDto> choices;

	public String getQuizUid() {
		return quizUid;
	}

	public void setQuizUid(String quizUid) {
		this.quizUid = quizUid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ZonedDateTime getBegin() {
		return begin;
	}

	public void setBegin(ZonedDateTime begin) {
		this.begin = begin;
	}

	public ZonedDateTime getEnd() {
		return end;
	}

	public void setEnd(ZonedDateTime end) {
		this.end = end;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<QuestionDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDto> questions) {
		this.questions = questions;
	}

	public List<ChoiceDto> getChoices() {
		return choices;
	}

	public void setChoices(List<ChoiceDto> choices) {
		this.choices = choices;
	}

	public static class Builder {
		private QuizCompleteDto instance;

		public Builder() {
			instance = new QuizCompleteDto();
		}

		public Builder withQuizUid(String quizUid) {
			instance.setQuizUid(quizUid);
			return this;
		}

		public Builder withDescription(String description) {
			instance.setDescription(description);
			return this;
		}

		public Builder withBegin(ZonedDateTime begin) {
			instance.setBegin(begin);
			return this;
		}

		public Builder withEnd(ZonedDateTime end) {
			instance.setEnd(end);
			return this;
		}

		public Builder withStatus(String status) {
			instance.setStatus(status);
			return this;
		}

		public Builder withQuestions(List<QuestionDto> questions) {
			instance.setQuestions(questions);
			return this;
		}

		public Builder withChoices(List<ChoiceDto> choices) {
			instance.setChoices(choices);
			return this;
		}

		public QuizCompleteDto build() {
			return instance;
		}

	}

}

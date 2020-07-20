package br.com.jitec.quiz.presentation.payload;

import java.time.ZonedDateTime;

public class QuizRequest {

	private String description;
	private ZonedDateTime begin;
	private ZonedDateTime end;

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

	public static class Builder {
		private QuizRequest instance;

		public Builder() {
			instance = new QuizRequest();
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

		public QuizRequest build() {
			return instance;
		}
	}

}

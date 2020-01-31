package br.com.jitec.quiz.presentation.payload;

import java.time.LocalDateTime;

public class QuizRequest {

	private String description;
	private LocalDateTime begin;
	private LocalDateTime end;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getBegin() {
		return begin;
	}

	public void setBegin(LocalDateTime begin) {
		this.begin = begin;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
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

		public Builder withBegin(LocalDateTime begin) {
			instance.setBegin(begin);
			return this;
		}

		public Builder withEnd(LocalDateTime end) {
			instance.setEnd(end);
			return this;
		}

		public QuizRequest build() {
			return instance;
		}
	}

}

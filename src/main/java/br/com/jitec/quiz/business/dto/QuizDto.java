package br.com.jitec.quiz.business.dto;

import java.time.LocalDateTime;

public class QuizDto {

	private String quizUid;
	private String description;
	private LocalDateTime begin;
	private LocalDateTime end;
	private String status;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static class Builder {
		private QuizDto instance;

		public Builder() {
			instance = new QuizDto();
		}

		public Builder withQuizUid(String quizUid) {
			instance.setQuizUid(quizUid);
			return this;
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

		public Builder withStatus(String status) {
			instance.setStatus(status);
			return this;
		}

		public QuizDto build() {
			return instance;
		}

	}

}

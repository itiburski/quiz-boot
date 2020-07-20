package br.com.jitec.quiz.presentation.payload;

import java.time.ZonedDateTime;

public class QuizResponse {

	private String quizUid;
	private String description;
	private ZonedDateTime begin;
	private ZonedDateTime end;
	private String status;

// 	Maybe return TemplateResponse, but without Question list

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

}

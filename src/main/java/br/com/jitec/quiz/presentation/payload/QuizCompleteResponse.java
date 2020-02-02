package br.com.jitec.quiz.presentation.payload;

import java.time.LocalDateTime;
import java.util.List;

public class QuizCompleteResponse {

	private String quizUid;
	private String description;
	private LocalDateTime begin;
	private LocalDateTime end;
	private String status;
	private List<QuestionResponse> questions;
	private List<ChoiceResponse> choices;

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

	public List<QuestionResponse> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionResponse> questions) {
		this.questions = questions;
	}

	public List<ChoiceResponse> getChoices() {
		return choices;
	}

	public void setChoices(List<ChoiceResponse> choices) {
		this.choices = choices;
	}

}

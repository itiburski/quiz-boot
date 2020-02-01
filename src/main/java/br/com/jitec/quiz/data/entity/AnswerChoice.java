package br.com.jitec.quiz.data.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "answer_choices")
public class AnswerChoice {

	@EmbeddedId
	private AnswerChoiceId id;

	@ManyToOne
	@MapsId("question_id")
	@JoinColumn(name = "question_id")
	private Question question;

	@ManyToOne
	@MapsId("answer_id")
	@JoinColumn(name = "answer_id")
	private Answer answer;

	private Choices choice;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public Choices getChoice() {
		return choice;
	}

	public void setChoice(Choices choice) {
		this.choice = choice;
	}

	public AnswerChoiceId getId() {
		return id;
	}

	public void setId(AnswerChoiceId id) {
		this.id = id;
	}

}

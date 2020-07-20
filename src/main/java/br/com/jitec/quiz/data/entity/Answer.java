package br.com.jitec.quiz.data.entity;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "answers")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;

	@OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
	private List<AnswerChoice> answerChoices;

	@Column(name = "dt_answer")
	private ZonedDateTime date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}

	public List<AnswerChoice> getAnswerChoices() {
		return answerChoices;
	}

	public void setAnswerChoices(List<AnswerChoice> answerChoices) {
		this.answerChoices = answerChoices;
	}

	public static class Builder {
		private Answer instance;

		public Builder() {
			instance = new Answer();
		}

		public Builder withId(Long id) {
			instance.setId(id);
			return this;
		}

		public Builder withQuiz(Quiz quiz) {
			instance.setQuiz(quiz);
			return this;
		}

		public Builder withAnswerChoices(List<AnswerChoice> answerChoices) {
			instance.setAnswerChoices(answerChoices);
			return this;
		}

		public Builder withDate(ZonedDateTime date) {
			instance.setDate(date);
			return this;
		}

		public Answer build() {
			return instance;
		}
	}

}

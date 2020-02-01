package br.com.jitec.quiz.data.entity;

import java.time.LocalDateTime;
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
@Table(name = "quizzes")
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 36)
	private String quizUid;

	@Column(length = 250)
	private String description;

	@Column(name = "dt_begin")
	private LocalDateTime begin;

	@Column(name = "dt_end")
	private LocalDateTime end;

	private StatusQuiz status;

	@ManyToOne
	@JoinColumn(name = "template_id")
	private Template template;

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	private List<Answer> answers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public StatusQuiz getStatus() {
		return status;
	}

	public void setStatus(StatusQuiz status) {
		this.status = status;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public static class Builder {
		private Quiz instance;

		public Builder() {
			instance = new Quiz();
		}

		public Builder withId(Long id) {
			instance.setId(id);
			return this;
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

		public Builder withStatus(StatusQuiz status) {
			instance.setStatus(status);
			return this;
		}

		public Builder withTemplate(Template template) {
			instance.setTemplate(template);
			return this;
		}

		public Quiz build() {
			return instance;
		}
	}

}

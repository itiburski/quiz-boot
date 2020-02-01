package br.com.jitec.quiz.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 36)
	private String uid;

	@Column(length = 250)
	private String description;

	@ManyToOne
	@JoinColumn(name = "template_id")
	private Template template;

//	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
//	private List<AnswerChoice> answerChoice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

//	public List<AnswerChoice> getAnswerChoice() {
//		return answerChoice;
//	}
//
//	public void setAnswerChoice(List<AnswerChoice> answerChoice) {
//		this.answerChoice = answerChoice;
//	}

	public static class Builder {
		private Question instance;

		public Builder() {
			instance = new Question();
		}

		public Builder withId(Long id) {
			instance.setId(id);
			return this;
		}

		public Builder withUid(String uid) {
			instance.setUid(uid);
			return this;
		}

		public Builder withDescription(String description) {
			instance.setDescription(description);
			return this;
		}

		public Builder withTemplate(Template template) {
			instance.setTemplate(template);
			return this;
		}

		public Question build() {
			return instance;
		}
	}

}

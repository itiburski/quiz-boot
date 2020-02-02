package br.com.jitec.quiz.data.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "templates")
public class Template {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 36)
	private String uid;

	@Column(length = 100)
	private String description;

	@OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
	private List<Question> questions;

	@Enumerated
	private StatusTemplate status;

	@OneToMany(mappedBy = "template", fetch = FetchType.LAZY)
	private List<Quiz> quizzes;

	public Template() {
		this.setQuestions(new ArrayList<>());
	}

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

	public StatusTemplate getStatus() {
		return status;
	}

	public void setStatus(StatusTemplate status) {
		this.status = status;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public static class Builder {
		private Template instance;

		public Builder() {
			instance = new Template();
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

		public Builder withStatus(StatusTemplate status) {
			instance.setStatus(status);
			return this;
		}

		public Builder withQuestions(List<Question> questions) {
			instance.setQuestions(questions);
			return this;
		}

		public Template build() {
			return instance;
		}
	}

}

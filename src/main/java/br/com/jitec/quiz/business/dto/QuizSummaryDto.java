package br.com.jitec.quiz.business.dto;

import java.util.List;

public class QuizSummaryDto {

	private String quizUid;
	private String description;
	private String status;
	private List<QuestionSummaryDto> questionsSummary;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<QuestionSummaryDto> getQuestionsSummary() {
		return questionsSummary;
	}

	public void setQuestionsSummary(List<QuestionSummaryDto> questionsSummary) {
		this.questionsSummary = questionsSummary;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("QuizSummaryDto [quizUid=").append(quizUid).append(", description=")
				.append(description).append(", status=").append(status).append(", questionsSummary=")
				.append(questionsSummary).append("]");
		return builder.toString();
	}

	public static class Builder {
		private QuizSummaryDto instance;

		public Builder() {
			instance = new QuizSummaryDto();
		}

		public Builder withQuizUid(String quizUid) {
			instance.setQuizUid(quizUid);
			return this;
		}

		public QuizSummaryDto build() {
			return instance;
		}
	}

}

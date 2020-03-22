package br.com.jitec.quiz.presentation.payload;

import java.util.List;

public class QuizSummaryResponse {

	private String quizUid;
	private String description;
	private String status;
	private List<QuestionSummaryResponse> questionsSummary;

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

	public List<QuestionSummaryResponse> getQuestionsSummary() {
		return questionsSummary;
	}

	public void setQuestionsSummary(List<QuestionSummaryResponse> questionsSummary) {
		this.questionsSummary = questionsSummary;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("QuizSummaryResponse [quizUid=").append(quizUid)
				.append(", description=").append(description).append(", status=").append(status)
				.append(", questionsSummary=").append(questionsSummary).append("]");
		return builder.toString();
	}

}

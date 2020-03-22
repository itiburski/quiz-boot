package br.com.jitec.quiz.presentation.payload;

import java.util.List;

public class QuestionSummaryResponse {

	private String questionUid;
	private String description;
	private List<ChoiceSummaryResponse> choicesSummary;

	public String getQuestionUid() {
		return questionUid;
	}

	public void setQuestionUid(String questionUid) {
		this.questionUid = questionUid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ChoiceSummaryResponse> getChoicesSummary() {
		return choicesSummary;
	}

	public void setChoicesSummary(List<ChoiceSummaryResponse> choicesSummary) {
		this.choicesSummary = choicesSummary;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("QuestionSummaryResponse [questionUid=").append(questionUid)
				.append(", description=").append(description).append(", choicesSummary=").append(choicesSummary)
				.append("]");
		return builder.toString();
	}

}

package br.com.jitec.quiz.business.dto;

import java.util.List;

public class QuestionSummaryDto {

	private String questionUid;
	private String description;
	private List<ChoiceSummaryDto> choicesSummary;

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

	public List<ChoiceSummaryDto> getChoicesSummary() {
		return choicesSummary;
	}

	public void setChoicesSummary(List<ChoiceSummaryDto> choicesSummary) {
		this.choicesSummary = choicesSummary;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("QuestionSummaryDto [questionUid=").append(questionUid)
				.append(", description=").append(description).append(", choicesSummary=").append(choicesSummary)
				.append("]");
		return builder.toString();
	}

}

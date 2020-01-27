package br.com.jitec.quiz.presentation.payload;

import io.swagger.annotations.ApiModelProperty;

public class QuestionResponse {

	@ApiModelProperty("Generated Question UID")
	private String questionUid;

	@ApiModelProperty("Question description")
	private String description;

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

}

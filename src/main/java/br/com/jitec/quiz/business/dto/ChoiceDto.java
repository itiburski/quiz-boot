package br.com.jitec.quiz.business.dto;

public class ChoiceDto {

	private String choice;
	private String description;

	public ChoiceDto(String choice, String description) {
		this.choice = choice;
		this.description = description;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

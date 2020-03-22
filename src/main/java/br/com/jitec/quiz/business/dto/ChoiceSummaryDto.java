package br.com.jitec.quiz.business.dto;

import br.com.jitec.quiz.data.entity.Choices;

public class ChoiceSummaryDto implements Comparable<ChoiceSummaryDto> {

	private String choice;
	private Integer quantity;

	public ChoiceSummaryDto() {
		super();
	}

	public ChoiceSummaryDto(String choice, Integer quantity) {
		this.choice = choice;
		this.quantity = quantity;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("ChoiceSummaryDto [choice=").append(choice).append(", quantity= ")
				.append(quantity.toString()).append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(ChoiceSummaryDto other) {
		int c1 = Choices.valueOf(this.getChoice()).getCode();
		int c2 = Choices.valueOf(other.getChoice()).getCode();

		return c1 - c2;
//		return c2 - c1;
	}

}

package br.com.jitec.quiz.presentation.payload;

public class ChoiceSummaryResponse {

	private String choice;
	private Integer quantity;

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
		StringBuilder builder = new StringBuilder("ChoiceSummaryResponse [choice=").append(choice)
				.append(", quantity= ").append(quantity.toString()).append("]");
		return builder.toString();
	}

}

package br.com.jitec.quiz.data.entity;

public enum Choices {

	TERRIBLE(1, "Terrible"), POOR(2, "Poor"), GOOD(3, "Good"), EXCELLENT(4, "Excellent");

	private Integer code;
	private String description;

	private Choices(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static Choices valueOf(Integer codeToFind) {
		for (Choices option : values()) {
			if (option.code.equals(codeToFind)) {
				return option;
			}
		}
		return null;
	}

}

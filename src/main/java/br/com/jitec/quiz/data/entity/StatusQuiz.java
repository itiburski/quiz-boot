package br.com.jitec.quiz.data.entity;

public enum StatusQuiz {

	PENDING(0, "Pending"), ACTIVE(1, "Active"), ENDED(2, "Ended");

	private Integer code;
	private String description;

	private StatusQuiz(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static StatusQuiz valueOf(Integer codeToFind) {
		for (StatusQuiz option : values()) {
			if (option.code.equals(codeToFind)) {
				return option;
			}
		}
		return null;
	}

}

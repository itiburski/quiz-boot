package br.com.jitec.quiz.data.entity;

public enum StatusTemplate {

	PENDING(0, "Pending"), ACTIVE(1, "Active"), INACTIVE(2, "Inactive");

	private Integer code;
	private String description;

	private StatusTemplate(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static StatusTemplate valueOf(Integer codeToFind) {
		for (StatusTemplate option : values()) {
			if (option.code.equals(codeToFind)) {
				return option;
			}
		}
		return null;
	}

}

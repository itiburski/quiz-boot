package br.com.jitec.quiz.data.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChoicesTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testValueOf() {
		Assertions.assertEquals(Choices.TERRIBLE, Choices.valueOf(0));
		Assertions.assertEquals(Choices.POOR, Choices.valueOf(1));
		Assertions.assertEquals(Choices.GOOD, Choices.valueOf(2));
		Assertions.assertEquals(Choices.EXCELLENT, Choices.valueOf(3));
		Integer code = null;
		Assertions.assertNull(Choices.valueOf(code));
	}

	@Test
	void testGetCode() {
		Assertions.assertEquals(0, Choices.TERRIBLE.getCode());
		Assertions.assertEquals(1, Choices.POOR.getCode());
		Assertions.assertEquals(2, Choices.GOOD.getCode());
		Assertions.assertEquals(3, Choices.EXCELLENT.getCode());
	}

	@Test
	void testGetDescription() {
		Assertions.assertEquals("Terrible", Choices.TERRIBLE.getDescription());
		Assertions.assertEquals("Poor", Choices.POOR.getDescription());
		Assertions.assertEquals("Good", Choices.GOOD.getDescription());
		Assertions.assertEquals("Excellent", Choices.EXCELLENT.getDescription());
	}

}

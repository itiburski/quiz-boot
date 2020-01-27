package br.com.jitec.quiz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	// http://localhost:8080/quiz/swagger-ui.html
	// http://localhost:8080/quiz/v2/api-docs

	@Bean
	public Docket apiDocket() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.jitec.quiz")).paths(PathSelectors.any()).build()
				.apiInfo(getApiInfo())
		;

		return docket;
	}

	private ApiInfo getApiInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("Quiz API")
				.description("This API allows quiz manipulation: templating and answering")
				.license("MIT License")
				.licenseUrl("https://opensource.org/licenses/MIT").version("1.0.0").build();

		return apiInfo;
	}

}

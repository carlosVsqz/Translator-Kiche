package com.nosysask.esc.translator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket parkingLotApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Translator-api").apiInfo(apiInfo()).select()
		.apis(RequestHandlerSelectors.basePackage(
				"com.nosysask.esc.translator.controller"))
		.build();

	}

		private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Translator Kiche API")
				.description("Translator API reference for developers")
				.termsOfServiceUrl("https://github.com/carlosVsqz/Translator-Kiche")
				.contact(new Contact("Translator kiche-es Application", "",
						"")).license("Translator License")
				.licenseUrl("https://github.com/carlosVsqz/Translator-Kiche").version("1.0").build();
	}

}

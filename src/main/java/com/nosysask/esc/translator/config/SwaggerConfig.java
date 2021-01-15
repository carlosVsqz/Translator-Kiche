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
		return new Docket(DocumentationType.SWAGGER_2).groupName("ParkingLot-api").apiInfo(apiInfo()).select()
		.apis(RequestHandlerSelectors.basePackage(
				"com.nosysask.esc.translator.controller"))
		.build();

	}

		private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Translator Kiche API")
				.description("Translator API reference for developers")
				.termsOfServiceUrl("http://example.com")
				.contact(new Contact("Translator kiche-es Application", "http://example.com",
						"parkinglot@gmail.com")).license("Translator License")
				.licenseUrl("parkinglot@gmail.com").version("1.0").build();
	}

}

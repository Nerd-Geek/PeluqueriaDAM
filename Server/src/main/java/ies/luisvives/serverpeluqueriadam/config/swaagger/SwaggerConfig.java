package ies.luisvives.serverpeluqueriadam.config.swaagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableWebMvc // Importante con el nuevo Swagger3 y Spring 2.6.x
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("ies.luisvives.serverpeluqueriadam.controller"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        return new ApiInfo(
                "API REST Peluquería",
                "API REST para una peluquería",
                "1.1",
                "Terms of Service",
                new Contact("IES Luis Vives", "https://www.iesluisvives.es/",
                        " info@iesluisvives.es"),
                "MIT",
                null, new ArrayList<>()
        );
    }
}

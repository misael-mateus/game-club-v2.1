package br.com.projetoweb.configuration;

import br.com.projetoweb.dto.GameDTO;
import br.com.projetoweb.dto.PartnerDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
//@EnableSwagger2 // Não é necessário para a versão 3.0.0 do Swagger
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.projetoweb"))
                .build()
                .ignoredParameterTypes( GameDTO.class,
                        Pageable.class,Page.class,PartnerDTO.class, Sort.class)
                .apiInfo(metaInfo());
    }

    ApiInfo metaInfo() {
        return new ApiInfo(
                "Projeto Web API REST",
                "API REST de cadastro de jogos.",
                "1.0",
                "Terms of Service",
                new Contact("Misael Mateus", "https://github.com/misael-mateus",
                        "misaelmateus.java@gmail.com"), "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>());
    }

}

package comidev.apicerseufisi.swagger;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("comidev")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        final String URL = "https://api.whatsapp.com/send?phone=51960949984&text=";
        return new OpenAPI()
                .info(new Info()
                        .title("API Cerseu FISI")
                        .description("Hola pendejos. Si ven algún error (y de seguro lo verán)"
                                + " me avisan; si tienen un idea, me avisan; si quieren que cree"
                                + " un endpoint que duelva cierta cosa, me avisan y me dan el formato"
                                + " de cómo es la 'cosa'. Estamos en desarrollo aún, es normal que no salga perfecto gaaaaa")
                        .version("v0.0.1")
                        .contact(new Contact()
                                .name("Omar Miranda")
                                .email("comidev.contacto@gmail.com")
                                .url(URL))
                        .license(new License()
                                .name("Omar")
                                .url(URL)))
                .components(new Components()
                        .addSecuritySchemes("bearer-key", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}

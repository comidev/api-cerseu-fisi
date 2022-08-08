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
                        .description("<b>Gente me avisan o me mandan msg por whatsapp si:</b>"
                                + "<ul>"
                                + "     <li>Ven algún error que es muy probable que lo vean.</li>"
                                + "     <li>Quieren que modifique el cuerpo de retorno o lo que retorna la petición</li>"
                                + "     <li>Quieren que cree un nuevo endpoint</li>"
                                + "</ul>"
                                + "<p>NOTA: Sea el punto 3 o 4 también manden el formato del cuerpo en JSON</p>"
                                + "<p>Analicen bien los endpoints puede que en verdad todo esté bien solo que fallaron en algo.</p>"
                                + "<p>Igual estamos en desarrollo así que normal fallar.</p>")
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

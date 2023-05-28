package alveradkatsuro.com.br.meu_bolsista.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Responsável pelas configurações do Swagger.
 *
 * @author Alfredo Gabriel
 * @since 26/03/2023
 * @version 1.0
 */
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("Bearer",
								new SecurityScheme().type(SecurityScheme.Type.HTTP)
										.scheme("bearer").bearerFormat("JWT")))
				.info(new Info().title("Meu Egresso API")
						.description("API de Egressos da Universidade Federal do Pará.")
						.version("v0.0.1")
						.license(new License().name("GNU GENERAL PUBLIC LICENSE V3")
								.url("https://github.com/LabEsUFPA/meuEgresso/blob/main/LICENSE")))
				.externalDocs(new ExternalDocumentation()
						.description("Wiki do Projeto no Github")
						.url("https://github.com/LabEsUFPA/meuEgresso/wiki"));
	}
}

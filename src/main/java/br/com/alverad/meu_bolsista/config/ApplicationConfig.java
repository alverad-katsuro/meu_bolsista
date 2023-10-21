package br.com.alverad.meu_bolsista.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.alverad.meu_bolsista.config.properties.AnexosStoreApiProperties;
import br.com.alverad.meu_bolsista.config.properties.CorsProperties;
import br.com.alverad.meu_bolsista.config.properties.KeycloakProperties;
import br.com.alverad.meu_bolsista.config.properties.RegistrationDefaultProperties;
import br.com.alverad.meu_bolsista.util.SetConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Responsável pelas configurações gerais da aplicação.
 *
 * @author Alfredo Gabriel
 * @since 26/03/2023
 * @version 1.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = { CorsProperties.class, RegistrationDefaultProperties.class,
		AnexosStoreApiProperties.class, KeycloakProperties.class })
public class ApplicationConfig {

	private final RegistrationDefaultProperties registrationDefaultProperties;

	@Bean
	public OAuth2AuthorizedClientManager authorizedClientManager(
			ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientService authorizedClientService) {

		OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
				.clientCredentials()
				.build();

		AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
				clientRegistrationRepository, authorizedClientService);
		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

		return authorizedClientManager;
	}

	/**
	 * Create a Webclient wired with the OAuth2AuthorizedClientManager to generate
	 * the Authorization token
	 *
	 * @param authorizedClientManager
	 * @return
	 */
	@Bean
	public WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {

		ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
				authorizedClientManager);

		oauth2Client.setDefaultClientRegistrationId(registrationDefaultProperties.registrationId());
		return WebClient.builder()
				.apply(oauth2Client.oauth2Configuration())
				.filter(logRequest())
				.build();
	}

	/**
	 * Log request for debugging purposes
	 *
	 * @return
	 */
	private static ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
			clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));

			return Mono.just(clientRequest);
		});
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.addConverter(new SetConverter());
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).isAmbiguityIgnored();
		return mapper;
	}
}

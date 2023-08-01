package alveradkatsuro.com.br.meu_bolsista.config.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import alveradkatsuro.com.br.meu_bolsista.config.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import alveradkatsuro.com.br.meu_bolsista.config.oauth2.handle.OAuth2AuthenticationFailureHandler;
import alveradkatsuro.com.br.meu_bolsista.config.oauth2.handle.OAuth2AuthenticationSuccessHandler;
import alveradkatsuro.com.br.meu_bolsista.config.oauth2.service.CustomOAuth2UserService;
import alveradkatsuro.com.br.meu_bolsista.config.properties.CorsProperties;
import lombok.RequiredArgsConstructor;

/**
 * Responsável pelas configurações de segurança da aplicação.
 *
 * @author Alfredo Gabriel
 * @since 26/03/2023
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CorsProperties corsProperties;

	private final CustomOAuth2UserService customOAuth2UserService;

	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

	/*
	 * By default, Spring OAuth2 uses
	 * HttpSessionOAuth2AuthorizationRequestRepository to save
	 * the authorization request. But, since our service is stateless, we can't save
	 * it in
	 * the session. We'll save the request in a Base64 encoded cookie instead.
	 */
	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity, RequestMatcherBuilder mvc) throws Exception {

		return httpSecurity.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.anonymous(AbstractHttpConfigurer::disable)
				.formLogin(forms -> forms.disable())
				.httpBasic(basic -> basic.disable())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exeption -> exeption
						.authenticationEntryPoint(
								new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
				.oauth2Login(oauth -> oauth.authorizationEndpoint(endpoint -> endpoint.baseUri(
						"/oauth2/authorize").authorizationRequestRepository(
								cookieAuthorizationRequestRepository()))
						// .tokenEndpoint(null) aqui da pra retornar o refresh estudar
						.redirectionEndpoint(red -> red.baseUri("/oauth2/callback/*"))
						.userInfoEndpoint(user -> user.userService(
								customOAuth2UserService))
						.successHandler(oAuth2AuthenticationSuccessHandler)
						.failureHandler(oAuth2AuthenticationFailureHandler))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(
								mvc.matchers(
										"/auth/**",
										"/v3/api-docs/**",
										"/swagger-ui/**",
										"/swagger-ui.html",
										"/login",
										"/error",
										"/favicon.ico",
										"/auth/**",
										"/oauth2/**",
										"/"))
						.permitAll()
						.requestMatchers(mvc.matchers(HttpMethod.GET, "/planoTrabalho**/**")).permitAll()
						.requestMatchers(mvc.matchers(HttpMethod.GET, "/processoSeletivo**/**")).permitAll()
						.requestMatchers(mvc.matchers(HttpMethod.GET, "/arquivo/**")).permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(tokenAuthenticationFilter(),
						UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of(corsProperties.getAllowedOrigins()));
		configuration.setAllowedHeaders(List.of("Authorization"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type", "Content-Type",
				"Access-Control-Allow-Headers", "Access-Control-Allow-Origin"));
		configuration.setExposedHeaders(
				Arrays.asList("X-Get-Header", "Access-Control-Allow-Methods", "Access-Control-Allow-Origin",
						"Location"));
		configuration.setAllowedMethods(Collections.singletonList("*"));

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}

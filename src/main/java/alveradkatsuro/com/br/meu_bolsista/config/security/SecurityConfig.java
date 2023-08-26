package alveradkatsuro.com.br.meu_bolsista.config.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
@RequiredArgsConstructor
public class SecurityConfig {

	private final CorsProperties corsProperties;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity, RequestMatcherBuilder mvc) throws Exception {

		return httpSecurity.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.anonymous(AbstractHttpConfigurer::disable)
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
						.requestMatchers(mvc.matchers(HttpMethod.GET, "/planoTrabalho**/**"))
						.permitAll()
						.requestMatchers(mvc.matchers(HttpMethod.GET, "/processoSeletivo**/**"))
						.permitAll()
						.requestMatchers(mvc.matchers(HttpMethod.GET, "/arquivo/**")).permitAll()
						.requestMatchers(mvc.matchers(HttpMethod.GET, "/**")).permitAll()
						.requestMatchers(mvc.matchers("/teste/**")).permitAll()
						.anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2
						.jwt(Customizer.withDefaults()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exeption -> exeption
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
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
	@SuppressWarnings("unchecked")
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
				jwt -> {
					Object roles = jwt.getClaimAsMap("realm_access").get("roles");
					if (roles instanceof List<?>) {
						List<String> rolesList = (List<String>) roles;
						JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();
						Collection<GrantedAuthority> allAuthorities = scopesConverter.convert(jwt);
						allAuthorities.addAll(
								rolesList.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList());
						return allAuthorities;
					} else {
						return Collections.emptyList();
					}
				});

		return jwtAuthenticationConverter;
	}

}

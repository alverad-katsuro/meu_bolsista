package alveradkatsuro.com.br.meu_bolsista.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

import alveradkatsuro.com.br.meu_bolsista.config.properties.CorsProperties;
import alveradkatsuro.com.br.meu_bolsista.config.properties.OauthProperties;
import alveradkatsuro.com.br.meu_bolsista.config.properties.RsaKeyProperties;
import alveradkatsuro.com.br.meu_bolsista.config.properties.TokenProperties;
import alveradkatsuro.com.br.meu_bolsista.service.usuario.UsuarioService;
import alveradkatsuro.com.br.meu_bolsista.util.SetConverter;
import lombok.RequiredArgsConstructor;

/**
 * Responsável pelas configurações gerais da aplicação.
 *
 * @author Alfredo Gabriel
 * @since 26/03/2023
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = { RsaKeyProperties.class, TokenProperties.class, CorsProperties.class,
		OauthProperties.class })
public class ApplicationConfig {

	private final UsuarioService usuarioService;

	@Bean
	public UserDetailsService userDetailsService() {
		return usuarioService;
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		return new ProviderManager(authenticationProvider);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.addConverter(new SetConverter());
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).isAmbiguityIgnored();
		return mapper;
	}
}

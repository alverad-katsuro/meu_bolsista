package br.com.alverad.meu_bolsista.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "services")
public record RegistrationDefaultProperties (String registrationId) {

}

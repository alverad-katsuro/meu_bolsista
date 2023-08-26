package alveradkatsuro.com.br.meu_bolsista.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public record KeycloakProperties(String adminUrl, String rolesUrl, String usersUrl) {

    public String append(String... values) {
        StringBuilder builder = new StringBuilder(usersUrl);
        for (String string : values) {
            builder.append("/").append(string);
        }
        return builder.toString();
    }
}

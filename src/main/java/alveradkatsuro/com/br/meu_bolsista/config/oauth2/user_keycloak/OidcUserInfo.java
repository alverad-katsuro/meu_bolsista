package alveradkatsuro.com.br.meu_bolsista.config.oauth2.user_keycloak;

import java.util.Map;

public abstract sealed class OidcUserInfo permits KeycloakOidcUserInfo {
    protected Map<String, Object> attributes;

    protected OidcUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract Boolean getEmailVerified();

    public abstract String getImageUrl();
}

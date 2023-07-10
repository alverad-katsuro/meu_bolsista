package alveradkatsuro.com.br.meu_bolsista.config.oauth2.oidc_user;

import java.util.Map;

public abstract sealed class OidcUserInfoCustom permits KeycloakOidcUserInfo {
    protected Map<String, Object> attributes;

    protected OidcUserInfoCustom(Map<String, Object> attributes) {
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

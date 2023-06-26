package alveradkatsuro.com.br.meu_bolsista.config.oauth2.oidc_user;

import java.util.Map;

import alveradkatsuro.com.br.meu_bolsista.enumeration.AuthProvider;
import alveradkatsuro.com.br.meu_bolsista.exceptions.OAuth2AuthenticationProcessingException;

public class OidcUserInfoFactory {

    private OidcUserInfoFactory() {
    }

    public static OidcUserInfo getOidcUserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.KEYCLOAK.toString())) {
            return new KeycloakOidcUserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(
                    "Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}

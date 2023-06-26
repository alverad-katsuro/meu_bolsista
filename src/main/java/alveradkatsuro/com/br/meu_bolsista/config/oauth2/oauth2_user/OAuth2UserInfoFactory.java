package alveradkatsuro.com.br.meu_bolsista.config.oauth2.oauth2_user;

import java.util.Map;

import alveradkatsuro.com.br.meu_bolsista.enumeration.AuthProvider;
import alveradkatsuro.com.br.meu_bolsista.exceptions.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {

    private OAuth2UserInfoFactory() {
    }

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.FACEBOOK.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.GITHUB.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(
                    "Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
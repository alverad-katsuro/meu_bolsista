package alveradkatsuro.com.br.meu_bolsista.config.oauth2.oidc_user;

import java.util.Map;

public final class KeycloakOidcUserInfo extends OidcUserInfoCustom {
    public KeycloakOidcUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public Boolean getEmailVerified() {
        return (Boolean) attributes.get("email_verified");
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getImageUrl() {
        if (attributes.containsKey("picture")) {
            Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");
            if (pictureObj.containsKey("data")) {
                Map<String, Object> dataObj = (Map<String, Object>) pictureObj.get("data");
                if (dataObj.containsKey("url")) {
                    return (String) dataObj.get("url");
                }
            }
        }
        return "";
    }
}

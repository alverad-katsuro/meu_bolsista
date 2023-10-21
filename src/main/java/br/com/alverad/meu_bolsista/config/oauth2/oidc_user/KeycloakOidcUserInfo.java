package br.com.alverad.meu_bolsista.config.oauth2.oidc_user;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public final class KeycloakOidcUserInfo extends OidcUserInfoCustom implements Authentication {

    private Boolean authenticated;

    public KeycloakOidcUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    public KeycloakOidcUserInfo(JwtAuthenticationToken token) {
        super(token.getTokenAttributes());
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
    public String getPreferredUsername() {
        return (String) attributes.get("preferred_username");
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

    @SuppressWarnings("unchecked")
    public Set<String> getRoles() {

        if (attributes.get("realm_access") instanceof Map<?, ?> realms
                && realms.get("roles") instanceof List<?> rolesObject) {
            return ((List<String>) rolesObject).stream().collect(Collectors.toSet());
        }
        return Set.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public Object getCredentials() {
        return this;
    }

    @Override
    public Object getDetails() {
        return this;
    }

    @Override
    public Object getPrincipal() {
        return this;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

}

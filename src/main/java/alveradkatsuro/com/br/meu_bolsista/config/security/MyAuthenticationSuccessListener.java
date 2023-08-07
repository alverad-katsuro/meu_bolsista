package alveradkatsuro.com.br.meu_bolsista.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import alveradkatsuro.com.br.meu_bolsista.config.oauth2.oidc_user.KeycloakOidcUserInfo;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import alveradkatsuro.com.br.meu_bolsista.repository.usuario.UsuarioRepository;

@Component
public class MyAuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        JwtAuthenticationToken authToken = event.getAuthentication() instanceof JwtAuthenticationToken
                ? (JwtAuthenticationToken) event.getAuthentication()
                : null;
        if (authToken != null) {
            KeycloakOidcUserInfo keycloakOidcUserInfo = new KeycloakOidcUserInfo(authToken);
            UsuarioModel usuario = usuarioRepository.findById(keycloakOidcUserInfo.getId())
                    .orElse(UsuarioModel.builder().email(keycloakOidcUserInfo.getEmail())
                            .nome(keycloakOidcUserInfo.getName()).roles(keycloakOidcUserInfo.getRoles())
                            .id(keycloakOidcUserInfo.getId())
                            .imagemUrl(keycloakOidcUserInfo.getImageUrl())
                            .username(keycloakOidcUserInfo.getPreferredUsername()).build());
            usuarioRepository.save(usuario);
            keycloakOidcUserInfo.setAuthenticated(true);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(keycloakOidcUserInfo);
        }
    }

}

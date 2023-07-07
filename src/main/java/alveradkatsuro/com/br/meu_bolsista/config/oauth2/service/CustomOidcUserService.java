package alveradkatsuro.com.br.meu_bolsista.config.oauth2.service;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import alveradkatsuro.com.br.meu_bolsista.config.oauth2.oidc_user.OidcUserInfoCustom;
import alveradkatsuro.com.br.meu_bolsista.config.oauth2.oidc_user.OidcUserInfoFactory;
import alveradkatsuro.com.br.meu_bolsista.enumeration.AuthProvider;
import alveradkatsuro.com.br.meu_bolsista.enumeration.Authority;
import alveradkatsuro.com.br.meu_bolsista.exceptions.OAuth2AuthenticationProcessingException;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import alveradkatsuro.com.br.meu_bolsista.repository.usuario.UsuarioRepository;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public static final Logger logger = LoggerFactory.getLogger(CustomOidcUserService.class);

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        final OidcUser oidcUser = super.loadUser(userRequest);
        try {
            return processOidcUser(userRequest, oidcUser);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the
            // OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OidcUser processOidcUser(OidcUserRequest oidcUserRequest, OidcUser oidcUser) {
        OidcUserInfoCustom oidcUserInfo = OidcUserInfoFactory.getOidcUserInfo(
                oidcUserRequest.getClientRegistration().getRegistrationId(), oidcUser.getAttributes());
        if (oidcUserInfo.getEmail().isEmpty()) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<UsuarioModel> userOptional = usuarioRepository.findByEmailIgnoreCase(oidcUserInfo.getEmail());
        UsuarioModel user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getProvider()
                    .equals(AuthProvider
                            .findProviderName(oidcUserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oidcUserInfo, oidcUserRequest);
        } else {
            user = registerNewUser(oidcUserRequest, oidcUserInfo);
        }

        return user;
    }

    private UsuarioModel registerNewUser(OidcUserRequest oidcUserRequest, OidcUserInfoCustom oidcUserInfo) {
        UsuarioModel user = new UsuarioModel();

        user.setProvider(AuthProvider.findProviderName(oidcUserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oidcUserInfo.getId());
        user.setNome(oidcUserInfo.getName());
        user.setEmail(oidcUserInfo.getEmail());
        setAuthority(user, oidcUserRequest);
        user.setImagemUrl(oidcUserInfo.getImageUrl());
        user = usuarioRepository.save(user);
        user.setOidcIdToken(oidcUserRequest.getIdToken());
        user.setClaims(oidcUserRequest.getIdToken().getClaims());
        return user;
    }

    private UsuarioModel updateExistingUser(UsuarioModel existingUser, OidcUserInfoCustom oidcUserInfo,
            OidcUserRequest oidcUserRequest) {
        existingUser.setNome(oidcUserInfo.getName());
        existingUser.setImagemUrl(oidcUserInfo.getImageUrl());
        setAuthority(existingUser, oidcUserRequest);
        existingUser = usuarioRepository.save(existingUser);
        existingUser.setOidcIdToken(oidcUserRequest.getIdToken());
        existingUser.setClaims(oidcUserRequest.getIdToken().getClaims());
        return existingUser;
    }

    @SuppressWarnings("unchecked")
    private void setAuthority(UsuarioModel usuario, OidcUserRequest request) {
        Decoder decoder = Base64.getUrlDecoder();
        String[] token = request.getAccessToken().getTokenValue().split("\\.");
        Map<String, Object> atributos;
        try {
            atributos = objectMapper.readValue(new String(decoder.decode(token[1])),
                    new TypeReference<Map<String, Object>>() {
                    });
            if (atributos.get("realm_access") instanceof Map<?, ?> realms
                    && realms.get("roles") instanceof List<?> rolesObject) {
                Set<Authority> authoritys = new HashSet<>();
                ((List<String>) rolesObject).stream().forEach(role -> {
                    try {
                        authoritys.add(Authority.valueOf(role));
                    } catch (IllegalArgumentException e) {
                        logger.info("Authority not Allowed", e);
                    }
                });
                usuario.setAuthorities(authoritys);
            }
        } catch (JsonProcessingException ex) {
            // Throwing an instance of AuthenticationException will trigger the
            // OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }
}
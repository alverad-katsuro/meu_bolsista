package alveradkatsuro.com.br.meu_bolsista.service.keycloak.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import alveradkatsuro.com.br.meu_bolsista.config.properties.KeycloakProperties;
import alveradkatsuro.com.br.meu_bolsista.dto.keycloak.role.RealmRoleKeycloak;
import alveradkatsuro.com.br.meu_bolsista.dto.keycloak.user.UserDataKeycloak;
import alveradkatsuro.com.br.meu_bolsista.dto.usuario.UsuarioBasicDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.usuario.UsuarioDTO;
import alveradkatsuro.com.br.meu_bolsista.enumeration.Authority;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.service.keycloak.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final WebClient webClient;

    private final KeycloakProperties keycloakProperties;

    @Override
    public List<RealmRoleKeycloak> getRealmRoles() {
        return webClient.get()
                .uri(keycloakProperties.rolesUrl())
                .retrieve()
                .bodyToFlux(RealmRoleKeycloak.class)
                .collectList()
                .block();
    }

    @Override
    public RealmRoleKeycloak getRealmRole(String name) throws NotFoundException {
        return webClient.get()
                .uri(keycloakProperties.rolesUrl(), uriBuilder -> uriBuilder.queryParam("search", name).build())
                .retrieve()
                .bodyToFlux(RealmRoleKeycloak.class)
                .collectList()
                .blockOptional().orElseThrow(NotFoundException::new).stream()
                .filter(role -> role.getName().equals(name)).findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public RealmRoleKeycloak getRealmRole(Authority authority) throws NotFoundException {
        return getRealmRole(authority.toString());
    }

    @Override
    public List<UserDataKeycloak> getUsers() {
        return webClient.get()
                .uri(keycloakProperties.usersUrl())
                .retrieve()
                .bodyToFlux(UserDataKeycloak.class)
                .collectList()
                .block();
    }

    @Override
    public UserDataKeycloak getUser(String id) throws NotFoundException {
        return webClient.get()
                .uri(keycloakProperties.usersAppend(id))
                .retrieve()
                .bodyToMono(UserDataKeycloak.class)
                .onErrorResume(WebClientResponseException.class,
                        ex -> ex.getStatusCode() == HttpStatusCode.valueOf(404) ? Mono.empty() : Mono.error(ex))
                .blockOptional()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<UserDataKeycloak> getUserInRole(Authority authority) {
        return getUserInRole(authority.toString());
    }

    @Override
    public List<UserDataKeycloak> getUserInRole(String authority) {
        return webClient.get()
                .uri(keycloakProperties.rolesAppend(authority, "users"))
                .retrieve()
                .bodyToFlux(UserDataKeycloak.class)
                .onErrorResume(WebClientResponseException.class,
                        ex -> ex.getStatusCode() == HttpStatusCode.valueOf(404) ? Flux.empty() : Flux.error(ex))
                .collectList()
                .block();
    }

    @Override
    public void updateUser(UserDataKeycloak userDataKeycloak) {
        webClient.put()
                .uri(keycloakProperties.usersAppend(userDataKeycloak.getId()))
                .bodyValue(userDataKeycloak)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }

    @Override
    public void addRole(String id, Authority... authorities) {
        List<RealmRoleKeycloak> realmRoleKeycloaks = Arrays.stream(authorities).parallel().map(t -> {
            try {
                return getRealmRole(t);
            } catch (NotFoundException e) {
                log.error("Error in retrieve authority {} - {}", t.toString(), e.getMessage());
                return null;
            }
        }).toList();
        webClient.post()
                .uri(keycloakProperties.usersAppend(id, "role-mappings/realm"))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(realmRoleKeycloaks)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(throwable -> log.error("Error in add Role: {}", throwable.getMessage()))
                .subscribe();
    }

    @Override
    public void deleteRole(String id, Authority... authorities) {
        List<RealmRoleKeycloak> realmRoleKeycloaks = Arrays.stream(authorities).parallel().map(t -> {
            try {
                return getRealmRole(t);
            } catch (NotFoundException e) {
                log.error("Error in retrieve authority {} - {}", t.toString(), e.getMessage());
                return null;
            }
        }).toList();
        webClient.method(HttpMethod.DELETE)
                .uri(keycloakProperties.usersAppend(id, "role-mappings/realm"))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(realmRoleKeycloaks)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(throwable -> log.error("Error in delete Role: {}", throwable.getMessage()))
                .subscribe();
    }

    @Override
    public UsuarioDTO getUserDTO(String id) throws NotFoundException {
        UserDataKeycloak userDataKeycloak = getUser(id);
        return UsuarioDTO.builder()
                .id(userDataKeycloak.getId())
                .nome(String.format("%s %s", userDataKeycloak.getFirstName(), userDataKeycloak.getLastName()))
                .email(userDataKeycloak.getEmail())
                .picture(userDataKeycloak.getPicture())
                .lattes(userDataKeycloak.getLattes())
                .build();
    }

    @Override
    public UsuarioBasicDTO getUserBasicDTO(String id) throws NotFoundException {
        UserDataKeycloak userDataKeycloak = getUser(id);
        return UsuarioBasicDTO.builder()
                .id(userDataKeycloak.getId())
                .nome(String.format("%s %s", userDataKeycloak.getFirstName(), userDataKeycloak.getLastName()))
                .email(userDataKeycloak.getEmail())
                .build();
    }
}

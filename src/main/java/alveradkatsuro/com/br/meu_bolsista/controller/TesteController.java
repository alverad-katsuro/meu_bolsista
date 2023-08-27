package alveradkatsuro.com.br.meu_bolsista.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.dto.keycloak.role.RealmRoleKeycloak;
import alveradkatsuro.com.br.meu_bolsista.dto.keycloak.user.UserDataKeycloak;
import alveradkatsuro.com.br.meu_bolsista.enumeration.Authority;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.service.keycloak.KeycloakService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/teste")
public class TesteController {

    private final KeycloakService keycloakService;

    @GetMapping(value = "/roles")
    public List<RealmRoleKeycloak> getRealmRoles() {
        return keycloakService.getRealmRoles();
    }

    @GetMapping(value = "/roles", params = "authority")
    public RealmRoleKeycloak getRealmRoles(@RequestParam(required = false) Authority authority)
            throws NotFoundException {
        return keycloakService.getRealmRole(authority);
    }

    @GetMapping(value = "/users")
    public List<UserDataKeycloak> getUsers() {
        return keycloakService.getUsers();
    }

    @GetMapping(value = "/users/{id}")
    public UserDataKeycloak getUsers(@PathVariable String id) throws NotFoundException {
        return keycloakService.getUser(id);
    }

    @PostMapping(value = "/users/{id}", params = { "authority" })
    public void addRole(@PathVariable String id, @RequestParam(required = false) Authority[] authority) {
        keycloakService.addRole(id, authority);
    }

    @PutMapping(value = "/users")
    public void updateUser(@RequestBody UserDataKeycloak userDataKeycloak) {
        keycloakService.updateUser(userDataKeycloak);
    }

    @DeleteMapping(value = "/users/{id}", params = { "authority" })
    public void deleteRole(@PathVariable String id, @RequestParam(required = false) Authority[] authority) {
        keycloakService.deleteRole(id, authority);
    }

}

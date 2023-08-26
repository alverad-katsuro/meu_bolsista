package alveradkatsuro.com.br.meu_bolsista.service.keycloak;

import java.util.List;

import alveradkatsuro.com.br.meu_bolsista.dto.keycloak.role.RealmRoleKeycloak;
import alveradkatsuro.com.br.meu_bolsista.dto.keycloak.user.UserDataKeycloak;
import alveradkatsuro.com.br.meu_bolsista.enumeration.Authority;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;

public interface KeycloakService {

    public List<RealmRoleKeycloak> getRealmRoles();

    public RealmRoleKeycloak getRealmRole(String name) throws NotFoundException;

    public RealmRoleKeycloak getRealmRole(Authority authority) throws NotFoundException;

    public List<UserDataKeycloak> getUsers();

    public UserDataKeycloak getUser(String id) throws NotFoundException;

    public void addRole(String id, Authority... authorities);

    public void deleteRole(String id, Authority... authorities);

    public void updateUser(UserDataKeycloak userDataKeycloak);

}

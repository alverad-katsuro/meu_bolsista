package br.com.alverad.meu_bolsista.service.keycloak;

import java.util.List;

import br.com.alverad.meu_bolsista.dto.keycloak.role.RealmRoleKeycloak;
import br.com.alverad.meu_bolsista.dto.keycloak.user.UserDataKeycloak;
import br.com.alverad.meu_bolsista.dto.usuario.UsuarioBasicDTO;
import br.com.alverad.meu_bolsista.dto.usuario.UsuarioDTO;
import br.com.alverad.meu_bolsista.enumeration.Authority;
import br.com.alverad.meu_bolsista.exceptions.NotFoundException;

public interface KeycloakService {

    public List<RealmRoleKeycloak> getRealmRoles();

    public RealmRoleKeycloak getRealmRole(String name) throws NotFoundException;

    public RealmRoleKeycloak getRealmRole(Authority authority) throws NotFoundException;

    public List<UserDataKeycloak> getUsers();

    public UserDataKeycloak getUser(String id) throws NotFoundException;

    public void addRole(String id, Authority... authorities);

    public void deleteRole(String id, Authority... authorities);

    public void updateUser(UserDataKeycloak userDataKeycloak);

    public UsuarioDTO getUserDTO(String id) throws NotFoundException;

    public UsuarioBasicDTO getUserBasicDTO(String id) throws NotFoundException;

    public List<UserDataKeycloak> getUserInRole(Authority authority);

    public List<UserDataKeycloak> getUserInRole(String authority);

}

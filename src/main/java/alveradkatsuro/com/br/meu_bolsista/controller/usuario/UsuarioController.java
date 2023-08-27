package alveradkatsuro.com.br.meu_bolsista.controller.usuario;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.annotation.CurrentUserToken;
import alveradkatsuro.com.br.meu_bolsista.dto.keycloak.user.UserDataKeycloak;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.service.keycloak.KeycloakService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/usuario")
public class UsuarioController {

    private final KeycloakService keycloakService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public UserDataKeycloak getCurrentUser(@CurrentUserToken String id) throws NotFoundException {
        return keycloakService.getUser(id);
    }

}

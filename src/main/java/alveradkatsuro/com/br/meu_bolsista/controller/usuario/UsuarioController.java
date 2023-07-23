package alveradkatsuro.com.br.meu_bolsista.controller.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.annotation.CurrentUser;
import alveradkatsuro.com.br.meu_bolsista.enumeration.Authority;
import alveradkatsuro.com.br.meu_bolsista.exceptions.ResourceNotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import alveradkatsuro.com.br.meu_bolsista.projection.usuario.novo_plano.UsuarioNovoPlanoProjection;
import alveradkatsuro.com.br.meu_bolsista.repository.usuario.UsuarioRepository;
import alveradkatsuro.com.br.meu_bolsista.service.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public UsuarioModel getCurrentUser(@CurrentUser UsuarioModel userPrincipal) {
        return usuarioService.findById(userPrincipal.getId());
    }

    @GetMapping(value = "/estaNoPlanoTrabalho")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public List<UsuarioNovoPlanoProjection> estaNoPlanoTrabalho(
            @RequestParam(required = false, defaultValue = "0") Integer planoTrabalhoId,
            @RequestParam(required = false, defaultValue = "ROLE_PESQUISADOR") Authority authority) {
        return usuarioService.findUsuariosNotInPlanoTrabalho(planoTrabalhoId, authority);
    }

}

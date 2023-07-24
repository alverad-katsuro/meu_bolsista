package alveradkatsuro.com.br.meu_bolsista.controller.usuario_plano_trabalho;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.enumeration.Authority;
import alveradkatsuro.com.br.meu_bolsista.projection.usuario_plano_trabalho.novo_plano.UsuarioNovoPlanoProjection;
import alveradkatsuro.com.br.meu_bolsista.service.usuario_plano_trabalho.UsuarioPlanoTrabalhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/usuarioPlanoTrabalho")
public class UsuarioPlanoTrabalhoController {

    private final UsuarioPlanoTrabalhoService usuarioPlanoTrabalhoService;

    @GetMapping(value = "/estaNoPlanoTrabalho")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public List<UsuarioNovoPlanoProjection> estaNoPlanoTrabalho(
            @RequestParam(required = false, defaultValue = "0") Integer planoTrabalhoId,
            @RequestParam(required = false, defaultValue = "ROLE_PESQUISADOR") Authority authority) {
        return usuarioPlanoTrabalhoService.findAllUsuariosInPlanoTrabalho(planoTrabalhoId, authority);
    }
}

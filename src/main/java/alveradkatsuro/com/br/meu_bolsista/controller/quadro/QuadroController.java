package alveradkatsuro.com.br.meu_bolsista.controller.quadro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.dto.quadro.painel.QuadroPainelDTO;
import alveradkatsuro.com.br.meu_bolsista.service.quadro.QuadroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quadro")
public class QuadroController {

    private final QuadroService quadroService;

    @GetMapping(value = "/painel")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public Page<QuadroPainelDTO> consultarTarefas(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "15", required = false) Integer size,
            @RequestParam(defaultValue = "ASC", required = false) Direction direction) {
        return quadroService.findAllForPanel(page, size, direction);
    }

}

package alveradkatsuro.com.br.meu_bolsista.controller.processo_seletivo;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.annotation.CurrentUserToken;
import alveradkatsuro.com.br.meu_bolsista.controller.arquivo.ArquivoController;
import alveradkatsuro.com.br.meu_bolsista.dto.processo_seletivo.ProcessoSeletivoDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.processo_seletivo.ProcessoSeletivoPlanoTabalhoDTO;
import alveradkatsuro.com.br.meu_bolsista.enumeration.ResponseType;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.processo_seletivo.ProcessoSeletivoModel;
import alveradkatsuro.com.br.meu_bolsista.projection.processo_seletivo.ProcessoSeletivoProjection;
import alveradkatsuro.com.br.meu_bolsista.service.keycloak.KeycloakService;
import alveradkatsuro.com.br.meu_bolsista.service.processo_seletivo.ProcessoSeletivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/processoSeletivo")
public class ProcessoSeletivoController {

    private final ModelMapper mapper;

    private final KeycloakService keycloakService;

    private final ProcessoSeletivoService processoSeletivoService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Page<ProcessoSeletivoDTO> findAll(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "15", required = false) Integer size,
            @RequestParam(defaultValue = "ASC", required = false) Direction direction,
            @RequestParam(defaultValue = "id", required = false) String[] properties) {
        return processoSeletivoService.findAll(page, size, direction, properties, ProcessoSeletivoDTO.class,
                ProcessoSeletivoProjection.class).map(ps -> {
                    try {
                        ps.getPlanoTrabalho().setCapaUrl(
                                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                                        ArquivoController.class)
                                        .recuperarArquivo(ps.getPlanoTrabalho().getCapaResourceId()))
                                        .toUri().toString());
                    } catch (IllegalArgumentException | SecurityException | IOException | NotFoundException e) {
                        log.catching(e);
                        return null;
                    }
                    return ps;
                });
    }

    @GetMapping(value = "/planoTrabalhoDisponiveis")
    public List<ProcessoSeletivoPlanoTabalhoDTO> findAllPlanoTrabalhoDisponiveis() {
        return processoSeletivoService.findProcessosDisponiveis();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ProcessoSeletivoDTO findById(@PathVariable Integer id, @CurrentUserToken String usuarioId) {
        ProcessoSeletivoDTO processoSeletivoDTO = processoSeletivoService.findById(id, ProcessoSeletivoDTO.class,
                ProcessoSeletivoProjection.class);
        processoSeletivoDTO.getCandidatos().forEach(e -> {
            if (e.getId() != null) {
                try {
                    var usuarioBasicDTO = keycloakService.getUserBasicDTO(e.getId().getUsuarioId());
                    e.setUsuario(usuarioBasicDTO);
                } catch (NotFoundException e1) {
                    log.error("Não Foi encontrado o usuario com id: {}", e.getId().getUsuarioId());
                }
            }
            if (usuarioId != null && !processoSeletivoDTO.isInscrito()) {
                processoSeletivoDTO.setInscrito(e.getId().getUsuarioId().equals(usuarioId));
            }
        });

        return processoSeletivoDTO;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<ResponseType> create(@RequestBody ProcessoSeletivoDTO processoSeletivoDTO) {
        ProcessoSeletivoModel processoSeletivo = processoSeletivoService
                .save(mapper.map(processoSeletivoDTO, ProcessoSeletivoModel.class));
        return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                this.getClass()).findById(processoSeletivo.getId(), null))
                .toUri()).body(ResponseType.SUCESS_SAVE);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<ResponseType> update(@RequestBody ProcessoSeletivoDTO processoSeletivoDTO)
            throws NotFoundException {
        ProcessoSeletivoModel processoSeletivo = processoSeletivoService
                .update(mapper.map(processoSeletivoDTO, ProcessoSeletivoModel.class));
        return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                this.getClass()).findById(processoSeletivo.getId(), null))
                .toUri()).body(ResponseType.SUCESS_UPDATE);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public void deleteById(@PathVariable Integer id) {
        processoSeletivoService.deleteById(id);
    }

}

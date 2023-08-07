package alveradkatsuro.com.br.meu_bolsista.controller.usuario_processo_seletivo;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.annotation.CurrentUser;
import alveradkatsuro.com.br.meu_bolsista.controller.arquivo.ArquivoController;
import alveradkatsuro.com.br.meu_bolsista.dto.usuario_processo_seletivo.UsuarioProcessoSeletivoCreateDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.usuario_processo_seletivo.UsuarioProcessoSeletivoDTO;
import alveradkatsuro.com.br.meu_bolsista.enumeration.ResponseType;
import alveradkatsuro.com.br.meu_bolsista.exceptions.DeadlineRegistrationException;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.processo_seletivo.ProcessoSeletivoModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModel;
import alveradkatsuro.com.br.meu_bolsista.projection.usuario_processo_seletivo.UsuarioProcessoSeletivoProjection;
import alveradkatsuro.com.br.meu_bolsista.service.usuario_processo_seletivo.UsuarioProcessoSeletivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/usuarioProcessoSeletivo")
public class UsuarioProcessoSeletivoController {

        private final ModelMapper mapper;

        private final UsuarioProcessoSeletivoService usuarioProcessoSeletivoService;

        @GetMapping
        @ResponseStatus(code = HttpStatus.OK)
        public Page<UsuarioProcessoSeletivoDTO> findAll(
                        @RequestParam(defaultValue = "0", required = false) Integer page,
                        @RequestParam(defaultValue = "15", required = false) Integer size,
                        @RequestParam(defaultValue = "ASC", required = false) Direction direction,
                        @RequestParam(defaultValue = "id", required = false) String[] properties) {
                return usuarioProcessoSeletivoService
                                .findAll(page, size, direction, properties, UsuarioProcessoSeletivoDTO.class,
                                                UsuarioProcessoSeletivoProjection.class)
                                .map(e -> {
                                        try {
                                                e.setCurriculo(WebMvcLinkBuilder
                                                                .linkTo(WebMvcLinkBuilder
                                                                                .methodOn(ArquivoController.class)
                                                                                .recuperarArquivo(e.getCurriculo()))
                                                                .toUri().toString());
                                        } catch (IllegalArgumentException | SecurityException | IOException
                                                        | NotFoundException e1) {
                                                e1.printStackTrace();
                                        }
                                        return e;
                                });
        }

        @ResponseStatus(code = HttpStatus.OK)
        @GetMapping(value = "/{usuarioId}/{processoSeletivoId}")
        public UsuarioProcessoSeletivoDTO findById(@PathVariable String usuarioId,
                        @PathVariable Integer processoSeletivoId) {
                return mapper.map(usuarioProcessoSeletivoService.findById(usuarioId, processoSeletivoId),
                                UsuarioProcessoSeletivoDTO.class);
        }

        @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
        @Operation(security = { @SecurityRequirement(name = "Bearer") })
        public ResponseEntity<ResponseType> create(@CurrentUser UsuarioModel usuario,
                        @ModelAttribute UsuarioProcessoSeletivoCreateDTO usuarioProcessoSeletivoCreateDTO)
                        throws IOException, DeadlineRegistrationException {
                UsuarioProcessoSeletivoModel usuarioProcessoSeletivo = usuarioProcessoSeletivoService
                                .save(UsuarioProcessoSeletivoModel.builder()
                                                .processoSeletivo(ProcessoSeletivoModel.builder()
                                                                .id(usuarioProcessoSeletivoCreateDTO
                                                                                .getProcessoSeletivoId())
                                                                .build())
                                                .usuario(usuario)
                                                .build(),
                                                usuarioProcessoSeletivoCreateDTO.getArquivo());
                return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                                this.getClass())
                                .findById(usuarioProcessoSeletivo.getId().getUsuarioId(),
                                                usuarioProcessoSeletivo.getId().getProcessoSeletivoId()))
                                .toUri()).body(ResponseType.SUCESS_SAVE);
        }

        @PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
        @PreAuthorize("hasRole('ADMIN')")
        @Operation(security = { @SecurityRequirement(name = "Bearer") })
        public ResponseEntity<ResponseType> update(
                        @ModelAttribute UsuarioProcessoSeletivoCreateDTO usuarioprocessoSeletivoCreateDTO)
                        throws NotFoundException, IOException {
                UsuarioProcessoSeletivoModel processoSeletivo = usuarioProcessoSeletivoService
                                .update(mapper.map(usuarioprocessoSeletivoCreateDTO,
                                                UsuarioProcessoSeletivoModel.class),
                                                usuarioprocessoSeletivoCreateDTO.getArquivo());
                return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                                this.getClass())
                                .findById(processoSeletivo.getId().getUsuarioId(),
                                                processoSeletivo.getId().getProcessoSeletivoId()))
                                .toUri()).body(ResponseType.SUCESS_UPDATE);
        }

        @PreAuthorize("hasRole('ADMIN')")
        @ResponseStatus(code = HttpStatus.NO_CONTENT)
        @DeleteMapping(value = "/{usuarioId}/{planoTrabalhoId}")
        @Operation(security = { @SecurityRequirement(name = "Bearer") })
        public void deleteById(@PathVariable String usuarioId, @PathVariable Integer planoTrabalhoId) {
                usuarioProcessoSeletivoService.deleteById(usuarioId, planoTrabalhoId);
        }
}

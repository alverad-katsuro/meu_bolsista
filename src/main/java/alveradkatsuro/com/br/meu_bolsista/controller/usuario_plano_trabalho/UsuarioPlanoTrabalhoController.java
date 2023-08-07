package alveradkatsuro.com.br.meu_bolsista.controller.usuario_plano_trabalho;

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

import alveradkatsuro.com.br.meu_bolsista.dto.usuario_processo_seletivo.UsuarioProcessoSeletivoDTO;
import alveradkatsuro.com.br.meu_bolsista.enumeration.ResponseType;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModel;
import alveradkatsuro.com.br.meu_bolsista.projection.usuario_plano_trabalho.novo_plano.UsuarioNovoPlanoProjection;
import alveradkatsuro.com.br.meu_bolsista.service.usuario_plano_trabalho.UsuarioPlanoTrabalhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/usuarioPlanoTrabalho")
public class UsuarioPlanoTrabalhoController {

        private final ModelMapper mapper;

        private final UsuarioPlanoTrabalhoService usuarioPlanoTrabalhoService;

        @GetMapping(value = "/estaNoPlanoTrabalho")
        @Operation(security = { @SecurityRequirement(name = "Bearer") })
        public List<UsuarioNovoPlanoProjection> estaNoPlanoTrabalho(
                        @RequestParam(required = false, defaultValue = "0") Integer planoTrabalhoId,
                        @RequestParam(required = false, defaultValue = "ROLE_PESQUISADOR") String role) {
                return usuarioPlanoTrabalhoService.findAllUsuariosInPlanoTrabalho(planoTrabalhoId, role);
        }

        @GetMapping
        @ResponseStatus(code = HttpStatus.OK)
        public Page<UsuarioProcessoSeletivoDTO> findAll(
                        @RequestParam(defaultValue = "0", required = false) Integer page,
                        @RequestParam(defaultValue = "15", required = false) Integer size,
                        @RequestParam(defaultValue = "ASC", required = false) Direction direction,
                        @RequestParam(defaultValue = "id", required = false) String[] properties) {
                return usuarioPlanoTrabalhoService.findAll(page, size, direction, properties)
                                .map(e -> mapper.map(e, UsuarioProcessoSeletivoDTO.class));
        }

        @GetMapping(value = "/{usuarioId}/{planoTrabalhoId}")
        @ResponseStatus(code = HttpStatus.OK)
        public UsuarioProcessoSeletivoDTO findById(@PathVariable String usuarioId,
                        @PathVariable Integer planoTrabalhoId) {
                return mapper.map(usuarioPlanoTrabalhoService.findById(usuarioId, planoTrabalhoId),
                                UsuarioProcessoSeletivoDTO.class);
        }

        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        @Operation(security = { @SecurityRequirement(name = "Bearer") })
        public ResponseEntity<ResponseType> create(@RequestBody UsuarioProcessoSeletivoDTO usuarioprocessoSeletivoDTO) {
                UsuarioPlanoTrabalhoModel processoSeletivo = usuarioPlanoTrabalhoService
                                .save(mapper.map(usuarioprocessoSeletivoDTO, UsuarioPlanoTrabalhoModel.class));
                return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                                this.getClass())
                                .findById(processoSeletivo.getId().getUsuarioId(),
                                                processoSeletivo.getId().getPlanoTrabalhoId()))
                                .toUri()).body(ResponseType.SUCESS_SAVE);
        }

        @PutMapping
        @PreAuthorize("hasRole('ADMIN')")
        @Operation(security = { @SecurityRequirement(name = "Bearer") })
        public ResponseEntity<ResponseType> update(@RequestBody UsuarioProcessoSeletivoDTO usuarioprocessoSeletivoDTO)
                        throws NotFoundException {
                UsuarioPlanoTrabalhoModel processoSeletivo = usuarioPlanoTrabalhoService
                                .update(mapper.map(usuarioprocessoSeletivoDTO, UsuarioPlanoTrabalhoModel.class));
                return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                                this.getClass())
                                .findById(processoSeletivo.getId().getUsuarioId(),
                                                processoSeletivo.getId().getPlanoTrabalhoId()))
                                .toUri()).body(ResponseType.SUCESS_UPDATE);
        }

        @DeleteMapping(value = "/{usuarioId}/{planoTrabalhoId}")
        @PreAuthorize("hasRole('ADMIN')")
        @ResponseStatus(code = HttpStatus.NO_CONTENT)
        @Operation(security = { @SecurityRequirement(name = "Bearer") })
        public void deleteById(@PathVariable String usuarioId, @PathVariable Integer planoTrabalhoId) {
                usuarioPlanoTrabalhoService.deleteById(usuarioId, planoTrabalhoId);
        }
}

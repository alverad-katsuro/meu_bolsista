package br.com.alverad.meu_bolsista.controller.usuario_processo_seletivo;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.alverad.meu_bolsista.annotation.CurrentUserToken;
import br.com.alverad.meu_bolsista.dto.usuario_processo_seletivo.UsuarioProcessoSeletivoDTO;
import br.com.alverad.meu_bolsista.enumeration.ResponseType;
import br.com.alverad.meu_bolsista.exceptions.DeadlineRegistrationException;
import br.com.alverad.meu_bolsista.model.processo_seletivo.ProcessoSeletivoModel;
import br.com.alverad.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModel;
import br.com.alverad.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModelId;
import br.com.alverad.meu_bolsista.projection.usuario_processo_seletivo.UsuarioProcessoSeletivoProjection;
import br.com.alverad.meu_bolsista.service.usuario_processo_seletivo.UsuarioProcessoSeletivoService;
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
						UsuarioProcessoSeletivoProjection.class);
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping(value = "/{usuarioId}/{processoSeletivoId}")
	public UsuarioProcessoSeletivoDTO findById(@PathVariable String usuarioId,
			@PathVariable Integer processoSeletivoId) {
		return mapper.map(usuarioProcessoSeletivoService.findById(usuarioId, processoSeletivoId),
				UsuarioProcessoSeletivoDTO.class);
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping(value = "/estouNoProcesso/{processoSeletivoId}")
	public boolean estouNoProcesso(@PathVariable Integer processoSeletivoId, @CurrentUserToken String usuarioId) {
		return usuarioProcessoSeletivoService.estouNoProcesso(processoSeletivoId, usuarioId);
	}

	@PostMapping(value = "/processo/{processoId}")
	@Operation(security = { @SecurityRequirement(name = "Bearer") })
	public ResponseEntity<ResponseType> create(@PathVariable Integer processoId, @CurrentUserToken String usuarioId)
			throws IOException, DeadlineRegistrationException {
		UsuarioProcessoSeletivoModel usuarioProcessoSeletivo = usuarioProcessoSeletivoService
				.save(UsuarioProcessoSeletivoModel.builder()
						.id(UsuarioProcessoSeletivoModelId.builder().usuarioId(usuarioId).build())
						.processoSeletivo(ProcessoSeletivoModel.builder()
								.id(processoId)
								.build())
						.build());
		return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
				this.getClass())
				.findById(usuarioProcessoSeletivo.getId().getUsuarioId(),
						usuarioProcessoSeletivo.getId().getProcessoSeletivoId()))
				.toUri()).body(ResponseType.SUCESS_SAVE);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/{usuarioId}/{planoTrabalhoId}")
	@Operation(security = { @SecurityRequirement(name = "Bearer") })
	public void deleteById(@PathVariable String usuarioId, @PathVariable Integer planoTrabalhoId) {
		usuarioProcessoSeletivoService.deleteById(usuarioId, planoTrabalhoId);
	}
}

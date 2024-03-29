package br.com.alverad.meu_bolsista.controller.plano_trabalho;

import java.io.IOException;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.alverad.meu_bolsista.annotation.CurrentUserToken;
import br.com.alverad.meu_bolsista.config.oauth2.oidc_user.KeycloakOidcUserInfo;
import br.com.alverad.meu_bolsista.config.oauth2.oidc_user.OidcUserInfoCustom;
import br.com.alverad.meu_bolsista.controller.arquivo.ArquivoController;
import br.com.alverad.meu_bolsista.dto.objetivo.ObjetivoDTO;
import br.com.alverad.meu_bolsista.dto.plano_trabalho.PlanoTrabalhoCreateDTO;
import br.com.alverad.meu_bolsista.dto.plano_trabalho.PlanoTrabalhoDTO;
import br.com.alverad.meu_bolsista.dto.recurso_material.RecursoMaterialDTO;
import br.com.alverad.meu_bolsista.dto.usuario.UsuarioBasicDTO;
import br.com.alverad.meu_bolsista.dto.usuario_plano_trabalho.UsuarioPlanoTrabalhoCreateDTO;
import br.com.alverad.meu_bolsista.enumeration.ResponseType;
import br.com.alverad.meu_bolsista.exceptions.NotFoundException;
import br.com.alverad.meu_bolsista.exceptions.UnsubmittedReportException;
import br.com.alverad.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;
import br.com.alverad.meu_bolsista.service.keycloak.KeycloakService;
import br.com.alverad.meu_bolsista.service.plano_trabalho.PlanoTrabalhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/planoTrabalho")
public class PlanoTrabalhoController {

	private final ModelMapper mapper;

	private final KeycloakService keycloakService;

	private final PlanoTrabalhoService planoTrabalhoService;

	@GetMapping
	@Operation(security = { @SecurityRequirement(name = "Bearer") })
	public Page<PlanoTrabalhoDTO> findAll(
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "20", required = false) Integer size,
			@RequestParam(defaultValue = "ASC", required = false) Direction direction) {

		return planoTrabalhoService.findAll(page, size, direction).map(this::fromModel);
	}

	@GetMapping(value = "/{id}")
	@Operation(security = { @SecurityRequirement(name = "Bearer") })
	public PlanoTrabalhoDTO findById(@PathVariable Integer id) {
		return fromModel(planoTrabalhoService.findById(id));
	}

	@GetMapping(value = "/{planoTrabalhoId}/estouNele")
	@Operation(security = { @SecurityRequirement(name = "Bearer") })
	public boolean isUsuarioNoPlano(@PathVariable Integer planoTrabalhoId, @CurrentUserToken String usuarioId) {
		return planoTrabalhoService.isUsuarioNoPlano(planoTrabalhoId, usuarioId);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(security = { @SecurityRequirement(name = "Bearer") })
	public ResponseEntity<ResponseType> save(
			@RequestPart("planoTrabalho") PlanoTrabalhoCreateDTO planoTrabalhoCreateDTO,
			@RequestPart("arquivo") MultipartFile arquivo,
			JwtAuthenticationToken token) throws IOException {
		OidcUserInfoCustom oidcUserInfoCustom = new KeycloakOidcUserInfo(token);

		PlanoTrabalhoModel planoTrabalho = planoTrabalhoService.save(oidcUserInfoCustom.getId(),
				planoTrabalhoCreateDTO, arquivo);

		return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
				PlanoTrabalhoController.class).findById(planoTrabalho.getId()))
				.toUri()).body(ResponseType.SUCESS_SAVE);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(security = { @SecurityRequirement(name = "Bearer") })
	public ResponseEntity<ResponseType> update(
			@RequestPart("planoTrabalho") PlanoTrabalhoCreateDTO planoTrabalhoCreateDTO,
			@RequestPart(required = false) MultipartFile arquivo)
			throws NotFoundException, IOException {

		PlanoTrabalhoModel planoTrabalho = planoTrabalhoService.update(planoTrabalhoCreateDTO, arquivo);

		return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
				PlanoTrabalhoController.class).findById(planoTrabalho.getId()))
				.toUri()).body(ResponseType.SUCESS_UPDATE);
	}

	@PutMapping(value = "/{planoTrabalhoId}/relatorio", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(security = { @SecurityRequirement(name = "Bearer") })
	public ResponseEntity<ResponseType> submeterRelatorio(@PathVariable Integer planoTrabalhoId,
			@RequestPart(required = true) MultipartFile arquivo) throws IOException {

		planoTrabalhoService.submeterRelatorio(planoTrabalhoId, arquivo);

		return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
				PlanoTrabalhoController.class).findById(planoTrabalhoId))
				.toUri()).body(ResponseType.RELATORIO_SUBMETIDO);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "/finalizar/{planoTrabalhoId}")
	public ResponseEntity<ResponseType> finalizarPlanoTrabalho(@PathVariable Integer planoTrabalhoId)
			throws UnsubmittedReportException {

		planoTrabalhoService.updateFinalizar(true, planoTrabalhoId);

		return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
				PlanoTrabalhoController.class).findById(planoTrabalhoId))
				.toUri()).body(ResponseType.PLANO_FINALIZADO);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "/reabrir/{planoTrabalhoId}")
	public ResponseEntity<ResponseType> reabrirPlanoTrabalho(@PathVariable Integer planoTrabalhoId)
			throws UnsubmittedReportException {

		planoTrabalhoService.updateFinalizar(false, planoTrabalhoId);

		return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
				PlanoTrabalhoController.class).findById(planoTrabalhoId))
				.toUri()).body(ResponseType.PLANO_REABERTO);
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Operation(security = { @SecurityRequirement(name = "Bearer") })
	public void deleteByID(@PathVariable Integer id) {
		planoTrabalhoService.deleteById(id);
	}

	private PlanoTrabalhoDTO fromModel(PlanoTrabalhoModel planoTrabalhoModel) {
		try {
			return PlanoTrabalhoDTO.builder()
					.areaTrabalho(planoTrabalhoModel.getAreaTrabalho())
					.descricao(planoTrabalhoModel.getDescricao())
					.id(planoTrabalhoModel.getId())
					.finalizado(planoTrabalhoModel.getFinalizado())
					.capaUrl(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
							ArquivoController.class).recuperarArquivo(planoTrabalhoModel.getCapaResourceId()))
							.toUri().toString())
					.capaResourceId(planoTrabalhoModel.getCapaResourceId())
					.relatorioResourceId(planoTrabalhoModel.getRelatorioResourceId())
					.relatorioUrl(planoTrabalhoModel.getRelatorioResourceId() != null ? WebMvcLinkBuilder
							.linkTo(WebMvcLinkBuilder.methodOn(
									ArquivoController.class)
									.recuperarArquivo(planoTrabalhoModel.getRelatorioResourceId()))
							.toUri().toString() : "")
					.titulo(planoTrabalhoModel.getTitulo())
					.objetivos(mapper.map(planoTrabalhoModel.getObjetivos(),
							new TypeToken<Set<ObjetivoDTO>>() {
							}.getType()))
					.recursoMateriais(mapper.map(planoTrabalhoModel.getRecursoMateriais(),
							new TypeToken<Set<RecursoMaterialDTO>>() {
							}.getType()))
					.pesquisadores(planoTrabalhoModel.getPesquisadores().stream()
							.map(e -> {
								try {
									UsuarioBasicDTO usuarioBasicDTO;
									usuarioBasicDTO = keycloakService.getUserBasicDTO(e.getId().getUsuarioId());
									return UsuarioPlanoTrabalhoCreateDTO.builder()
											.id(e.getId().getUsuarioId())
											.nome(usuarioBasicDTO.getNome())
											.planoTrabalhoId(e.getPlanoTrabalho().getId())
											.cargaHoraria(e.getCargaHoraria())
											.build();
								} catch (NotFoundException e1) {
									log.error("Usuário não encontrado: {}", e.getId().getUsuarioId());
									return UsuarioPlanoTrabalhoCreateDTO.builder()
											.id(e.getId().getUsuarioId())
											.nome("Usuário não encontrado")
											.planoTrabalhoId(e.getPlanoTrabalho().getId())
											.cargaHoraria(e.getCargaHoraria())
											.build();
								}

							})
							.collect(java.util.stream.Collectors.toSet()))
					.build();
		} catch (IllegalArgumentException | SecurityException | IOException | NotFoundException e) {
			log.catching(e);
			return null;
		}
	}

}

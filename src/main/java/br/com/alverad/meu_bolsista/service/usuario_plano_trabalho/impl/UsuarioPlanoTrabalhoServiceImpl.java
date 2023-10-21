package br.com.alverad.meu_bolsista.service.usuario_plano_trabalho.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.alverad.meu_bolsista.dto.keycloak.user.UserDataKeycloak;
import br.com.alverad.meu_bolsista.dto.usuario.UsuarioDTO;
import br.com.alverad.meu_bolsista.dto.usuario_plano_trabalho.UsuarioNovoPlanoDTO;
import br.com.alverad.meu_bolsista.dto.usuario_plano_trabalho.UsuarioPlanoDTO;
import br.com.alverad.meu_bolsista.exceptions.NotFoundException;
import br.com.alverad.meu_bolsista.exceptions.UsuarioPlanoNotFoundException;
import br.com.alverad.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModel;
import br.com.alverad.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModelId;
import br.com.alverad.meu_bolsista.projection.usuario_plano_trabalho.novo_plano.UsuarioNovoPlanoProjection;
import br.com.alverad.meu_bolsista.repository.usuario_plano_trabalho.UsuarioPlanoTrabalhoRepository;
import br.com.alverad.meu_bolsista.service.keycloak.KeycloakService;
import br.com.alverad.meu_bolsista.service.usuario_plano_trabalho.UsuarioPlanoTrabalhoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioPlanoTrabalhoServiceImpl implements UsuarioPlanoTrabalhoService {

	private final ModelMapper mapper;

	private final KeycloakService keycloakService;

	private final UsuarioPlanoTrabalhoRepository usuarioPlanoTrabalhoRepository;

	@Override
	public UsuarioPlanoTrabalhoModel findById(String usuarioId, Integer planoTrabalhoId) {
		return this.findById(new UsuarioPlanoTrabalhoModelId(usuarioId, planoTrabalhoId));
	}

	@Override
	public UsuarioPlanoTrabalhoModel findById(UsuarioPlanoTrabalhoModelId id) {
		return usuarioPlanoTrabalhoRepository.findById(id).orElseThrow();
	}

	@Override
	public List<UsuarioNovoPlanoDTO> findAllUsuariosInPlanoTrabalho(Integer planoTrabalhoId,
			String role) {

		List<UserDataKeycloak> userDataKeycloaks = keycloakService.getUserInRole(role);

		return userDataKeycloaks.stream().map(e -> {
			Optional<UsuarioNovoPlanoProjection> usuarioNovoPlanoProjection = usuarioPlanoTrabalhoRepository
					.findAllUsuariosInPlanoTrabalho(planoTrabalhoId, e.getId(),
							UsuarioNovoPlanoProjection.class);
			UsuarioNovoPlanoDTO dto;
			if (usuarioNovoPlanoProjection.isPresent()) {
				dto = mapper.map(usuarioNovoPlanoProjection.get(), UsuarioNovoPlanoDTO.class);
				dto.setNome(e.getFullName());
			} else {
				dto = UsuarioNovoPlanoDTO.builder()
						.id(e.getId())
						.nome(e.getFullName())
						.participante(false)
						.cargaHoraria(0)
						.planoTrabalhoId(planoTrabalhoId)
						.build();
			}
			return dto;
		}).toList();
	}

	@Override
	public void deleteById(UsuarioPlanoTrabalhoModelId id) {
		usuarioPlanoTrabalhoRepository.deleteById(id);
	}

	@Override
	public void deleteById(String usuarioId, Integer planoTrabalhoId) {
		this.deleteById(new UsuarioPlanoTrabalhoModelId(usuarioId, planoTrabalhoId));
	}

	@Override
	public Page<UsuarioPlanoTrabalhoModel> findAll(Integer page, Integer size, Direction direction,
			String[] properties) {
		return usuarioPlanoTrabalhoRepository.findAll(PageRequest.of(page, size, direction, properties));
	}

	@Override
	public <T> Page<T> findBy(Integer page, Integer size, Direction direction, String[] properties, Class<T> tipo) {
		return usuarioPlanoTrabalhoRepository.findBy(PageRequest.of(page, size, direction, properties), tipo);
	}

	@Override
	public UsuarioPlanoTrabalhoModel save(UsuarioPlanoTrabalhoModel usuarioPlanoTrabalho) {
		return usuarioPlanoTrabalhoRepository.save(usuarioPlanoTrabalho);
	}

	@Override
	public UsuarioPlanoTrabalhoModel update(UsuarioPlanoTrabalhoModel usuarioPlanoTrabalho) throws NotFoundException {
		if (usuarioPlanoTrabalhoRepository.existsById(usuarioPlanoTrabalho.getId())) {
			throw new NotFoundException();
		}
		return usuarioPlanoTrabalhoRepository.save(usuarioPlanoTrabalho);
	}

	@Override
	public List<UsuarioPlanoDTO> findUsuarioPlanoInPlanoTrabalho(Integer planoTrabalhoId) throws NotFoundException {
		try {
			return usuarioPlanoTrabalhoRepository.findByPlanoTrabalhoId(planoTrabalhoId).stream().map(e -> {
				UsuarioDTO usuarioDTO;
				try {
					usuarioDTO = keycloakService.getUserDTO(e.getId().getUsuarioId());
					return UsuarioPlanoDTO.builder()
							.cargaHoraria(e.getCargaHoraria())
							.usuario(usuarioDTO)
							.build();
				} catch (NotFoundException e1) {
					throw new UsuarioPlanoNotFoundException("Erro ao processar usuário", e1);
				}
			}).collect(Collectors.toList());
		} catch (RuntimeException e) {
			if (e.getCause() instanceof NotFoundException) {
				throw (NotFoundException) e.getCause();
			}
			throw e;
		}
	}

	@Override
	public boolean existsByIdUsuarioIdAndIdPlanoTrabalhoId(String usuarioId, Integer planoTrabalhoId) {
		return usuarioPlanoTrabalhoRepository.existsByIdUsuarioIdAndIdPlanoTrabalhoId(usuarioId, planoTrabalhoId);
	}
}

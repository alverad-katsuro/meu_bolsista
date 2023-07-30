package alveradkatsuro.com.br.meu_bolsista.service.usuario_plano_trabalho.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import alveradkatsuro.com.br.meu_bolsista.enumeration.Authority;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModelId;
import alveradkatsuro.com.br.meu_bolsista.projection.usuario_plano_trabalho.novo_plano.UsuarioNovoPlanoProjection;
import alveradkatsuro.com.br.meu_bolsista.repository.usuario_plano_trabalho.UsuarioPlanoTrabalhoRepository;
import alveradkatsuro.com.br.meu_bolsista.service.usuario_plano_trabalho.UsuarioPlanoTrabalhoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioPlanoTrabalhoServiceImpl implements UsuarioPlanoTrabalhoService {

	private final UsuarioPlanoTrabalhoRepository usuarioPlanoTrabalhoRepository;

	@Override
	public UsuarioPlanoTrabalhoModel findById(Integer usuarioId, Integer planoTrabalhoId) {
		return this.findById(new UsuarioPlanoTrabalhoModelId(usuarioId, planoTrabalhoId));
	}

	@Override
	public UsuarioPlanoTrabalhoModel findById(UsuarioPlanoTrabalhoModelId id) {
		return usuarioPlanoTrabalhoRepository.findById(id).orElseThrow();
	}

	@Override
	public List<UsuarioNovoPlanoProjection> findAllUsuariosInPlanoTrabalho(Integer planoTrabalhoId,
			Authority authority) {
		return usuarioPlanoTrabalhoRepository.findAllUsuariosInPlanoTrabalho(planoTrabalhoId, authority,
				UsuarioNovoPlanoProjection.class);
	}

	@Override
	public void deleteById(UsuarioPlanoTrabalhoModelId id) {
		usuarioPlanoTrabalhoRepository.deleteById(id);
	}

	@Override
	public void deleteById(Integer usuarioId, Integer planoTrabalhoId) {
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

}

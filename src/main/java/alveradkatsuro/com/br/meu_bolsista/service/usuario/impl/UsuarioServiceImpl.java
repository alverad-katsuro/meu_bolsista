package alveradkatsuro.com.br.meu_bolsista.service.usuario.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import alveradkatsuro.com.br.meu_bolsista.exceptions.InvalidRequestException;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import alveradkatsuro.com.br.meu_bolsista.repository.usuario.UsuarioRepository;
import alveradkatsuro.com.br.meu_bolsista.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;

/**
 * Implementação do Serviço responsável pelas rotinas internas da aplicação
 * referente ao usuário.
 *
 * @author Alfredo Gabriel
 * @since 26/03/2023
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;

	private final PasswordEncoder passwordEncoder;

	/**
	 * Metodo responsável por buscar um determinado usuário pelo seu username.
	 *
	 * @param authenticationRequest
	 * @return
	 * @author Alfredo Gabriel, Camilo Santos
	 * @since 16/04/2023
	 */
	@Override
	public UsuarioModel loadUserByUsername(String login) throws UsernameNotFoundException {
		try {
			return usuarioRepository.findByEmailIgnoreCase(login).orElseThrow();
		} catch (NoSuchElementException e) {
			throw new UsernameNotFoundException("User not found", e);
		}
	}

	@Override
	public boolean existsByUsername(String username) {
		return usuarioRepository.existsByEmailIgnoreCase(username);
	}

	@Override
	public UsuarioModel findById(Integer idUsuario) {
		return usuarioRepository.findById(idUsuario).orElseThrow();
	}

	@Override
	public List<UsuarioModel> findAll() {
		return usuarioRepository.findAll();
	}

	public UsuarioModel save(UsuarioModel usuario) {
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		return usuarioRepository.save(usuario);
	}

	public UsuarioModel update(UsuarioModel usuario) throws InvalidRequestException {
		if (usuario.getId() != null) {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			return usuarioRepository.save(usuario);
		} else {
			throw new InvalidRequestException();
		}
	}

	public boolean deleteById(Integer idUsuario) {
		if (usuarioRepository.existsById(idUsuario)) {
			usuarioRepository.deleteById(idUsuario);
			return true;
		}
		return false;
	}

	@Override
	public boolean existsByIdAndCreatedById(Integer id, Integer createdBy) {
		return usuarioRepository.existsByIdAndCreatedById(id, createdBy);
	}

}

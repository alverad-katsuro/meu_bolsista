package alveradkatsuro.com.br.meu_bolsista.service.usuario_processo_seletivo.impl;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import alveradkatsuro.com.br.meu_bolsista.exceptions.DeadlineRegistrationException;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModelId;
import alveradkatsuro.com.br.meu_bolsista.repository.usuario_processo_seletivo.UsuarioProcessoSeletivoRepository;
import alveradkatsuro.com.br.meu_bolsista.service.arquivo.ArquivoService;
import alveradkatsuro.com.br.meu_bolsista.service.usuario_processo_seletivo.UsuarioProcessoSeletivoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioProcessoSeletivoServiceImpl implements UsuarioProcessoSeletivoService {

	private final ModelMapper mapper;

	private final ArquivoService arquivoService;

	private final UsuarioProcessoSeletivoRepository usuarioProcessoSeletivoRepository;

	@Override
	public UsuarioProcessoSeletivoModel findById(String usuarioId, Integer processoSeletivoId) {
		return this.findById(new UsuarioProcessoSeletivoModelId(usuarioId, processoSeletivoId));
	}

	@Override
	public UsuarioProcessoSeletivoModel findById(UsuarioProcessoSeletivoModelId id) {
		return usuarioProcessoSeletivoRepository.findById(id).orElseThrow();
	}

	@Override
	public void deleteById(UsuarioProcessoSeletivoModelId id) {
		usuarioProcessoSeletivoRepository.deleteById(id);
	}

	@Override
	public void deleteById(String usuarioId, Integer planoTrabalhoId) {
		this.deleteById(new UsuarioProcessoSeletivoModelId(usuarioId, planoTrabalhoId));
	}

	@Override
	public Page<UsuarioProcessoSeletivoModel> findAll(Integer page, Integer size, Direction direction,
			String[] properties) {
		return usuarioProcessoSeletivoRepository.findAll(PageRequest.of(page, size, direction, properties));
	}

	@Override
	public <T, Z> Page<T> findAll(Integer page, Integer size, Direction direction, String[] properties, Class<T> type,
			Class<Z> projection) {
		Page<Z> p = usuarioProcessoSeletivoRepository.findAll(PageRequest.of(page, size, direction, properties),
				projection);
		return p.map(e -> mapper.map(e, type));
	}

	@Override
	public UsuarioProcessoSeletivoModel save(UsuarioProcessoSeletivoModel usuarioProcessoSeletivo,
			MultipartFile arquivo)
			throws IOException, DeadlineRegistrationException {
		if (!usuarioProcessoSeletivoRepository.inscricaoNoPrazo(usuarioProcessoSeletivo.getProcessoSeletivo().getId())) {
			throw new DeadlineRegistrationException();
		}
		ObjectId curriculoId = arquivoService.salvarArquivo(arquivo);
		usuarioProcessoSeletivo.setAprovado(false);
		usuarioProcessoSeletivo.setCurriculo(curriculoId.toString());
		return usuarioProcessoSeletivoRepository.save(usuarioProcessoSeletivo);
	}

	@Override
	public UsuarioProcessoSeletivoModel update(UsuarioProcessoSeletivoModel usuarioProcessoSeletivo,
			MultipartFile arquivo)
			throws NotFoundException, IOException {
		if (usuarioProcessoSeletivoRepository.existsById(usuarioProcessoSeletivo.getId())) {
			throw new NotFoundException();
		}
		ObjectId curriculoId = arquivoService.salvarArquivo(arquivo);
		usuarioProcessoSeletivo.setCurriculo(curriculoId.toString());
		return usuarioProcessoSeletivoRepository.save(usuarioProcessoSeletivo);
	}

}

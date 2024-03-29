package br.com.alverad.meu_bolsista.service.usuario_processo_seletivo;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import br.com.alverad.meu_bolsista.exceptions.DeadlineRegistrationException;
import br.com.alverad.meu_bolsista.exceptions.NotFoundException;
import br.com.alverad.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModel;
import br.com.alverad.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModelId;

public interface UsuarioProcessoSeletivoService {

	public UsuarioProcessoSeletivoModel findById(String usuarioId, Integer usuarioProcessoSeletivoId);

	public UsuarioProcessoSeletivoModel findById(UsuarioProcessoSeletivoModelId id);

	public Page<UsuarioProcessoSeletivoModel> findAll(Integer page, Integer size, Direction direction,
			String[] properties);

	public <T, Z> Page<T> findAll(Integer page, Integer size, Direction direction,
			String[] properties, Class<T> type, Class<Z> projection);

	public UsuarioProcessoSeletivoModel save(UsuarioProcessoSeletivoModel usuarioProcessoSeletivo)
			throws IOException, DeadlineRegistrationException;

	public UsuarioProcessoSeletivoModel update(UsuarioProcessoSeletivoModel usuarioProcessoSeletivo)
			throws NotFoundException, IOException;

	public void deleteById(UsuarioProcessoSeletivoModelId id);

	public void deleteById(String usuarioId, Integer usuarioProcessoSeletivoId);

	public boolean estouNoProcesso(Integer processoSeletivoId, String usuarioId);

}

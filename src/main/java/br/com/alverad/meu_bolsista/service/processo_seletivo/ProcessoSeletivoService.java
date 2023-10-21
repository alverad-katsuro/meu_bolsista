package br.com.alverad.meu_bolsista.service.processo_seletivo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import br.com.alverad.meu_bolsista.dto.processo_seletivo.ProcessoSeletivoPlanoTabalhoDTO;
import br.com.alverad.meu_bolsista.exceptions.NotFoundException;
import br.com.alverad.meu_bolsista.model.processo_seletivo.ProcessoSeletivoModel;

public interface ProcessoSeletivoService {

    public <T, Z> T findById(Integer id, Class<T> type, Class<Z> projection);

    public <T, Z> Page<T> findAll(Integer page, Integer size, Direction direction,
            String[] properties, Class<T> type, Class<Z> projection);

    public ProcessoSeletivoModel save(ProcessoSeletivoModel processoSeletivo);

    public ProcessoSeletivoModel update(ProcessoSeletivoModel processoSeletivo) throws NotFoundException;

    public void deleteById(Integer id);

    List<ProcessoSeletivoPlanoTabalhoDTO> findProcessosDisponiveis();

}

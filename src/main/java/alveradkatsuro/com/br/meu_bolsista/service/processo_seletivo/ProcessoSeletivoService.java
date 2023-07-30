package alveradkatsuro.com.br.meu_bolsista.service.processo_seletivo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.processo_seletivo.ProcessoSeletivoModel;

public interface ProcessoSeletivoService {

    public ProcessoSeletivoModel findById(Integer id);

    public Page<ProcessoSeletivoModel> findAll(Integer page, Integer size, Direction direction, String[] properties);

    public ProcessoSeletivoModel save(ProcessoSeletivoModel processoSeletivo);

    public ProcessoSeletivoModel update(ProcessoSeletivoModel processoSeletivo) throws NotFoundException;

    public void deleteById(Integer id);

}

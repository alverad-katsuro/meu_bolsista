package alveradkatsuro.com.br.meu_bolsista.service.processo_seletivo.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.processo_seletivo.ProcessoSeletivoModel;
import alveradkatsuro.com.br.meu_bolsista.repository.processo_seletivo.ProcessoSeletivoRepository;
import alveradkatsuro.com.br.meu_bolsista.service.processo_seletivo.ProcessoSeletivoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessoSeletivoServiceImpl implements ProcessoSeletivoService {

    private final ProcessoSeletivoRepository processoSeletivoRepository;

    @Override
    public void deleteById(Integer id) {
        processoSeletivoRepository.deleteById(id);
    }

    @Override
    public Page<ProcessoSeletivoModel> findAll(Integer page, Integer size, Direction direction, String[] properties) {
        return processoSeletivoRepository.findAll(PageRequest.of(page, size, direction, properties));
    }

    @Override
    public ProcessoSeletivoModel findById(Integer id) {
        return processoSeletivoRepository.findById(id).orElseThrow();
    }

    @Override
    public ProcessoSeletivoModel save(ProcessoSeletivoModel processoSeletivo) {
        return processoSeletivoRepository.save(processoSeletivo);
    }

    @Override
    public ProcessoSeletivoModel update(ProcessoSeletivoModel processoSeletivo) throws NotFoundException {
        if(!processoSeletivoRepository.existsById(processoSeletivo.getId())) {
            throw new NotFoundException();
        }
        return this.save(processoSeletivo);
    }


}

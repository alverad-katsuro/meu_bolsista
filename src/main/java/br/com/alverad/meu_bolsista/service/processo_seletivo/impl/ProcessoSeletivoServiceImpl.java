package br.com.alverad.meu_bolsista.service.processo_seletivo.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.alverad.meu_bolsista.dto.processo_seletivo.ProcessoSeletivoPlanoTabalhoDTO;
import br.com.alverad.meu_bolsista.exceptions.NotFoundException;
import br.com.alverad.meu_bolsista.model.processo_seletivo.ProcessoSeletivoModel;
import br.com.alverad.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModel;
import br.com.alverad.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModelId;
import br.com.alverad.meu_bolsista.repository.processo_seletivo.ProcessoSeletivoRepository;
import br.com.alverad.meu_bolsista.service.processo_seletivo.ProcessoSeletivoService;
import br.com.alverad.meu_bolsista.service.usuario_plano_trabalho.UsuarioPlanoTrabalhoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessoSeletivoServiceImpl implements ProcessoSeletivoService {

    private final ModelMapper mapper;

    private final ProcessoSeletivoRepository processoSeletivoRepository;

    private final UsuarioPlanoTrabalhoService usuarioPlanoTrabalhoService;

    @Override
    public void deleteById(Integer id) {
        processoSeletivoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public <T, Z> Page<T> findAll(Integer page, Integer size, Direction direction,
            String[] properties, Class<T> type, Class<Z> projection) {
        Page<Z> pageProjec = processoSeletivoRepository
                .findAll(PageRequest.of(page, size, direction, properties), projection);
        return pageProjec.map(e -> mapper.map(e, type));
    }

    @Override
    @Transactional
    public <T, Z> T findById(Integer id, Class<T> type, Class<Z> projection) {
        Z processoSeletivo = processoSeletivoRepository.findById(id, projection).orElseThrow();
        return mapper.map(processoSeletivo, type);
    }

    @Override
    public ProcessoSeletivoModel save(ProcessoSeletivoModel processoSeletivo) {
        return processoSeletivoRepository.save(processoSeletivo);
    }

    @Override
    public ProcessoSeletivoModel update(ProcessoSeletivoModel processoSeletivo) throws NotFoundException {
        if (!processoSeletivoRepository.existsById(processoSeletivo.getId())) {
            throw new NotFoundException();
        }
        var planoTrabalho = processoSeletivo.getPlanoTrabalho();
        processoSeletivo.getCandidatos().forEach(c -> {
            if (!c.getAprovado().booleanValue()) {
                usuarioPlanoTrabalhoService.deleteById(c.getId().getUsuarioId(), planoTrabalho.getId());
            } else if (c.getAprovado().booleanValue()
                    && !usuarioPlanoTrabalhoService.existsByIdUsuarioIdAndIdPlanoTrabalhoId(c.getId().getUsuarioId(),
                            planoTrabalho.getId())) {
                usuarioPlanoTrabalhoService.save(UsuarioPlanoTrabalhoModel.builder().cargaHoraria(0)
                        .planoTrabalho(planoTrabalho).id(UsuarioPlanoTrabalhoModelId.builder()
                                .usuarioId(c.getId().getUsuarioId()).planoTrabalhoId(planoTrabalho.getId()).build())
                        .build());
            }
        });
        return this.save(processoSeletivo);
    }

    @Override
    public List<ProcessoSeletivoPlanoTabalhoDTO> findProcessosDisponiveis() {
        return processoSeletivoRepository.findProcessosDisponiveis();
    }

}

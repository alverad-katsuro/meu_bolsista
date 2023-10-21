package br.com.alverad.meu_bolsista.service.plano_trabalho.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.alverad.meu_bolsista.dto.plano_trabalho.PlanoTrabalhoCreateDTO;
import br.com.alverad.meu_bolsista.dto.usuario_plano_trabalho.UsuarioPlanoTrabalhoCreateDTO;
import br.com.alverad.meu_bolsista.exceptions.NotFoundException;
import br.com.alverad.meu_bolsista.exceptions.UnsubmittedReportException;
import br.com.alverad.meu_bolsista.model.objetivo.ObjetivoModel;
import br.com.alverad.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;
import br.com.alverad.meu_bolsista.model.quadro.QuadroModel;
import br.com.alverad.meu_bolsista.model.recurso_material.RecursoMaterialModel;
import br.com.alverad.meu_bolsista.model.tarefa.TarefaDocument;
import br.com.alverad.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModel;
import br.com.alverad.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModelId;
import br.com.alverad.meu_bolsista.repository.plano_trabalho.PlanoTrabalhoRepository;
import br.com.alverad.meu_bolsista.service.arquivo.ArquivoService;
import br.com.alverad.meu_bolsista.service.plano_trabalho.PlanoTrabalhoService;
import br.com.alverad.meu_bolsista.service.tarefa.TarefaDocumentService;
import br.com.alverad.meu_bolsista.service.usuario_plano_trabalho.impl.UsuarioPlanoTrabalhoServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanoTrabalhoServiceImpl implements PlanoTrabalhoService {

    private final ModelMapper mapper;

    private final ArquivoService arquivoService;

    private final TarefaDocumentService tarefaService;

    private final PlanoTrabalhoRepository planoTrabalhoRepository;

    private final UsuarioPlanoTrabalhoServiceImpl usuarioPlanoTrabalhoService;

    @Override
    public void deleteById(Integer id) {
        planoTrabalhoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Page<PlanoTrabalhoModel> findAll(Integer page, Integer size, Direction direction) {
        return planoTrabalhoRepository.findBy(PageRequest.of(page, size, Sort.by(direction, "titulo")),
                PlanoTrabalhoModel.class);
    }

    @Override
    @Transactional
    public PlanoTrabalhoModel findById(Integer id) {
        return planoTrabalhoRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public PlanoTrabalhoModel save(String liderId, PlanoTrabalhoCreateDTO planoTrabalhoCreateDTO, MultipartFile arquivo)
            throws IOException {

        ObjectId capaResourceId = arquivoService.salvarArquivo(arquivo);

        PlanoTrabalhoModel planoTrabalho = mapper.map(planoTrabalhoCreateDTO, PlanoTrabalhoModel.class);
        planoTrabalho.setCapaResourceId(capaResourceId.toString());
        planoTrabalho.setLiderId(liderId);
        planoTrabalho.setQuadroModel(QuadroModel.builder().planoTrabalho(planoTrabalho).build());

        for (RecursoMaterialModel recurso : planoTrabalho.getRecursoMateriais()) {
            recurso.setPlanoTrabalho(planoTrabalho);
        }

        for (ObjetivoModel objetivo : planoTrabalho.getObjetivos()) {
            objetivo.setPlanoTrabalho(planoTrabalho);
        }

        planoTrabalho.getPesquisadores().clear();

        for (UsuarioPlanoTrabalhoCreateDTO pesquisador : planoTrabalhoCreateDTO.getPesquisadores()) {
            UsuarioPlanoTrabalhoModel usuarioPlanoTrabalhoModel = UsuarioPlanoTrabalhoModel.builder()
                    .id(UsuarioPlanoTrabalhoModelId.builder().usuarioId(pesquisador.getId()).build())
                    .planoTrabalho(planoTrabalho)
                    .cargaHoraria(pesquisador.getCargaHoraria())
                    .build();
            planoTrabalho.getPesquisadores()
                    .add(usuarioPlanoTrabalhoModel);
        }

        planoTrabalho.setFinalizado(false);

        planoTrabalho = planoTrabalhoRepository.save(planoTrabalho);

        List<TarefaDocument> tarefas = new ArrayList<>();
        for (ObjetivoModel objetivo : planoTrabalho.getObjetivos()) {
            tarefas.add(TarefaDocument.builder().titulo(objetivo
                    .getDescricao())
                    .quadroId(planoTrabalho.getQuadroModel().getId())
                    .objetivoId(objetivo.getId())
                    .build());
        }

        tarefaService.save(tarefas);

        return planoTrabalhoRepository.save(planoTrabalho);
    }

    @Override
    @Transactional
    public PlanoTrabalhoModel update(PlanoTrabalhoCreateDTO planoTrabalhoCreateDTO, MultipartFile arquivo)
            throws NotFoundException, IOException {

        if (!planoTrabalhoRepository.existsById(planoTrabalhoCreateDTO.getId())) {
            throw new NotFoundException();
        }

        mapper.getConfiguration().setSkipNullEnabled(true);

        PlanoTrabalhoModel planoTrabalho = this.findById(planoTrabalhoCreateDTO.getId());

        mapper.map(planoTrabalhoCreateDTO, planoTrabalho);

        if (arquivo != null) {
            ObjectId newResourceId = arquivoService.salvarArquivo(new ObjectId(planoTrabalhoCreateDTO.getCapaResourceId()),
                    arquivo);
            planoTrabalho.setCapaResourceId(newResourceId.toString());
        }


        for (RecursoMaterialModel recursoMaterialModel : planoTrabalho.getRecursoMateriais()) {
            recursoMaterialModel.setPlanoTrabalho(planoTrabalho);
        }
        for (ObjetivoModel objetivo : planoTrabalho.getObjetivos()) {
            objetivo.setPlanoTrabalho(planoTrabalho);
        }

        planoTrabalho.getPesquisadores().clear();
        for (UsuarioPlanoTrabalhoCreateDTO pesquisador : planoTrabalhoCreateDTO.getPesquisadores()) {
            UsuarioPlanoTrabalhoModel usuarioPlanoTrabalhoModel;
            try {
                usuarioPlanoTrabalhoModel = usuarioPlanoTrabalhoService.findById(pesquisador.getId(),
                        planoTrabalho.getId());
                usuarioPlanoTrabalhoModel.setCargaHoraria(pesquisador.getCargaHoraria());
            } catch (NoSuchElementException e) {
                usuarioPlanoTrabalhoModel = UsuarioPlanoTrabalhoModel.builder()
                        .id(UsuarioPlanoTrabalhoModelId.builder().usuarioId(pesquisador.getId()).build())
                        .planoTrabalho(planoTrabalho)
                        .cargaHoraria(pesquisador.getCargaHoraria())
                        .build();
            }
            planoTrabalho.getPesquisadores()
                    .add(usuarioPlanoTrabalhoModel);
        }
        planoTrabalho = planoTrabalhoRepository.save(planoTrabalho);

        List<TarefaDocument> tarefas = new ArrayList<>();
        for (ObjetivoModel objetivo : planoTrabalho.getObjetivos()) {
            if (!tarefaService.existsByObjetivoId(objetivo.getId())) {
                tarefas.add(TarefaDocument.builder().titulo(objetivo
                        .getDescricao())
                        .quadroId(planoTrabalho.getQuadroModel().getId())
                        .objetivoId(objetivo.getId())
                        .build());
            } else {
                TarefaDocument tarefa = tarefaService
                        .findByQuadroIdAndObjetivoId(planoTrabalho.getQuadroModel().getId(), objetivo.getId());
                tarefa.setTitulo(objetivo.getDescricao());
                tarefas.add(tarefa);
            }
        }

        tarefaService.save(tarefas);

        return planoTrabalho;
    }

    @Override
    @Transactional
    public void updateFinalizar(boolean isFinalizado, Integer planoTrabalhoId) throws UnsubmittedReportException {
        if (isFinalizado && !relatorioSubmetido(planoTrabalhoId)) {
            throw new UnsubmittedReportException();
        }
        planoTrabalhoRepository.updateFinalizar(isFinalizado, planoTrabalhoId);
    }

    @Override
    public boolean relatorioSubmetido(Integer planoTrabalhoId) {
        return planoTrabalhoRepository.existsByIdAndRelatorioResourceIdIsNotNull(planoTrabalhoId);
    }

    @Override
    public boolean isUsuarioNoPlano(Integer planoTrabalhoId, String usuarioId) {
        return planoTrabalhoRepository.existsByIdAndPesquisadoresId(planoTrabalhoId, usuarioId);
    }

    @Override
    @Transactional
    public void submeterRelatorio(Integer planoTrabalhoId, MultipartFile arquivo) throws IOException {

        ObjectId relatorioResourceId = arquivoService.salvarArquivo(arquivo);

        planoTrabalhoRepository.setRelatorioResourceId(planoTrabalhoId, relatorioResourceId.toString());

    }

}

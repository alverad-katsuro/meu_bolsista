package alveradkatsuro.com.br.meu_bolsista.controller.plano_trabalho;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.annotation.CurrentUser;
import alveradkatsuro.com.br.meu_bolsista.dto.objetivo.ObjetivoDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.plano_trabalho.PlanoTrabalhoDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.recurso_material.RecursoMaterialDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.usuario_plano_trabalho.UsuarioPlanoTrabalhoDTO;
import alveradkatsuro.com.br.meu_bolsista.enumeration.ResponseType;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.objetivo.ObjetivoModel;
import alveradkatsuro.com.br.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;
import alveradkatsuro.com.br.meu_bolsista.model.quadro.QuadroModel;
import alveradkatsuro.com.br.meu_bolsista.model.recurso_material.RecursoMaterialModel;
import alveradkatsuro.com.br.meu_bolsista.model.tarefa.TarefaDocument;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModel;
import alveradkatsuro.com.br.meu_bolsista.service.plano_trabalho.PlanoTrabalhoService;
import alveradkatsuro.com.br.meu_bolsista.service.tarefa.TarefaDocumentService;
import alveradkatsuro.com.br.meu_bolsista.service.usuario.UsuarioService;
import alveradkatsuro.com.br.meu_bolsista.service.usuario_plano_trabalho.impl.UsuarioPlanoTrabalhoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/planoTrabalho")
public class PlanoTrabalhoController {

    private final ModelMapper mapper;

    private final TarefaDocumentService tarefaService;

    private final UsuarioService usuarioService;

    private final PlanoTrabalhoService planoTrabalhoService;

    private final UsuarioPlanoTrabalhoServiceImpl usuarioPlanoTrabalhoService;

    @GetMapping
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public Page<PlanoTrabalhoDTO> findAll(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size,
            @RequestParam(defaultValue = "ASC", required = false) Direction direction) {
        return planoTrabalhoService.findAll(page, size, direction).map(this::fromModel);
    }

    @GetMapping(value = "/{id}")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public PlanoTrabalhoDTO findByID(@PathVariable Integer id) {
        return fromModel(planoTrabalhoService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<ResponseType> save(@RequestBody PlanoTrabalhoDTO planoTrabalhoDTO,
            @CurrentUser UsuarioModel usuario) {
        PlanoTrabalhoModel planoTrabalho = mapper.map(planoTrabalhoDTO, PlanoTrabalhoModel.class);
        planoTrabalho.setLider(usuario);
        planoTrabalho.setQuadroModel(QuadroModel.builder().planoTrabalho(planoTrabalho).build());
        for (RecursoMaterialModel recurso : planoTrabalho.getRecursoMateriais()) {
            recurso.setPlanoTrabalho(planoTrabalho);
        }
        for (ObjetivoModel objetivo : planoTrabalho.getObjetivos()) {
            objetivo.setPlanoTrabalho(planoTrabalho);
        }
        planoTrabalho.getPesquisadores().clear();
        for (UsuarioPlanoTrabalhoDTO pesquisador : planoTrabalhoDTO.getPesquisadores()) {
            UsuarioPlanoTrabalhoModel usuarioPlanoTrabalhoModel = UsuarioPlanoTrabalhoModel.builder()
                    .usuario(usuarioService.findById(pesquisador.getId()))
                    .planoTrabalho(planoTrabalho)
                    .cargaHoraria(pesquisador.getCargaHoraria())
                    .build();
            usuarioPlanoTrabalhoModel.getUsuario().getPlanosTrabalhos().add(usuarioPlanoTrabalhoModel);
            planoTrabalho.getPesquisadores()
                    .add(usuarioPlanoTrabalhoModel);
        }

        planoTrabalho = planoTrabalhoService.save(planoTrabalho);

        List<TarefaDocument> tarefas = new ArrayList<>();
        for (ObjetivoModel objetivo : planoTrabalho.getObjetivos()) {
            tarefas.add(TarefaDocument.builder().titulo(objetivo
                    .getDescricao())
                    .quadroId(planoTrabalho.getQuadroModel().getId())
                    .objetivoId(objetivo.getId())
                    .build());
        }

        tarefaService.save(tarefas);

        return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                PlanoTrabalhoController.class).findByID(planoTrabalho.getId()))
                .toUri()).body(ResponseType.SUCESS_SAVE);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<ResponseType> update(@RequestBody PlanoTrabalhoDTO planoTrabalhoDTO) throws NotFoundException {
        mapper.getConfiguration().setSkipNullEnabled(true);
        PlanoTrabalhoModel planoTrabalho = planoTrabalhoService.findById(planoTrabalhoDTO.getId());
        mapper.map(planoTrabalhoDTO, planoTrabalho);
        for (RecursoMaterialModel recursoMaterialModel : planoTrabalho.getRecursoMateriais()) {
            recursoMaterialModel.setPlanoTrabalho(planoTrabalho);
        }
        for (ObjetivoModel objetivo : planoTrabalho.getObjetivos()) {
            objetivo.setPlanoTrabalho(planoTrabalho);
        }
        planoTrabalho.getPesquisadores().clear();
        for (UsuarioPlanoTrabalhoDTO pesquisador : planoTrabalhoDTO.getPesquisadores()) {
            UsuarioPlanoTrabalhoModel usuarioPlanoTrabalhoModel;
            try {
                usuarioPlanoTrabalhoModel = usuarioPlanoTrabalhoService.findById(pesquisador.getId(),
                        planoTrabalho.getId());
                usuarioPlanoTrabalhoModel.setCargaHoraria(pesquisador.getCargaHoraria());
            } catch (NoSuchElementException e) {
                usuarioPlanoTrabalhoModel = UsuarioPlanoTrabalhoModel.builder()
                        .usuario(usuarioService.findById(pesquisador.getId()))
                        .planoTrabalho(planoTrabalho)
                        .cargaHoraria(pesquisador.getCargaHoraria())
                        .build();
            }

            usuarioPlanoTrabalhoModel.getUsuario().getPlanosTrabalhos().add(usuarioPlanoTrabalhoModel);
            planoTrabalho.getPesquisadores()
                    .add(usuarioPlanoTrabalhoModel);
        }
        planoTrabalho = planoTrabalhoService.update(planoTrabalho);

        List<TarefaDocument> tarefas = new ArrayList<>();
        for (ObjetivoModel objetivo : planoTrabalho.getObjetivos()) {
            if (!tarefaService.existsByObjetivoId(objetivo.getId())) {
                tarefas.add(TarefaDocument.builder().titulo(objetivo
                        .getDescricao())
                        .quadroId(planoTrabalho.getQuadroModel().getId())
                        .objetivoId(objetivo.getId())
                        .build());
            } else {
                TarefaDocument tarefa = tarefaService.findByQuadroIdAndObjetivoId(planoTrabalho.getQuadroModel().getId(), objetivo.getId());
                tarefa.setTitulo(objetivo.getDescricao());
                tarefas.add(tarefa);
            }
        }

        tarefaService.save(tarefas);

        return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                PlanoTrabalhoController.class).findByID(planoTrabalho.getId()))
                .toUri()).body(ResponseType.SUCESS_UPDATE);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(security = { @SecurityRequirement(name = "Bearer") })

    public void deleteByID(@PathVariable Integer id) {
        planoTrabalhoService.deleteById(id);
    }

    private PlanoTrabalhoDTO fromModel(PlanoTrabalhoModel planoTrabalhoModel) {
        return PlanoTrabalhoDTO.builder()
                .areaTrabalho(planoTrabalhoModel.getAreaTrabalho())
                .descricao(planoTrabalhoModel.getDescricao())
                .id(planoTrabalhoModel.getId())
                .liderNome(planoTrabalhoModel.getLider().getNome())
                .titulo(planoTrabalhoModel.getTitulo())
                .objetivos(mapper.map(planoTrabalhoModel.getObjetivos(), new TypeToken<Set<ObjetivoDTO>>() {
                }.getType()))
                .recursoMateriais(mapper.map(planoTrabalhoModel.getRecursoMateriais(),
                        new TypeToken<Set<RecursoMaterialDTO>>() {
                        }.getType()))
                .pesquisadores(planoTrabalhoModel.getPesquisadores().stream().map(e -> UsuarioPlanoTrabalhoDTO.builder()
                        .id(e.getUsuario().getId())
                        .planoTrabalhoId(e.getPlanoTrabalho().getId())
                        .cargaHoraria(e.getCargaHoraria())
                        .build()).collect(java.util.stream.Collectors.toSet()))
                .build();
    }

}

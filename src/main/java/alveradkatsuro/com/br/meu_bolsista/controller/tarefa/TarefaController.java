package alveradkatsuro.com.br.meu_bolsista.controller.tarefa;

import java.util.List;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.dto.tarefa.AtividadeIndexDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.tarefa.TarefaBasicDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.tarefa.TarefaDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.tarefa.TarefaDTO.AtividadeDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.tarefa.TarefaIndexDTO;
import alveradkatsuro.com.br.meu_bolsista.enumeration.ResponseType;
import alveradkatsuro.com.br.meu_bolsista.model.tarefa.AtividadeDocument;
import alveradkatsuro.com.br.meu_bolsista.model.tarefa.TarefaDocument;
import alveradkatsuro.com.br.meu_bolsista.service.tarefa.AtividadeDocumentService;
import alveradkatsuro.com.br.meu_bolsista.service.tarefa.TarefaDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tarefa")
public class TarefaController {

    private final ModelMapper mapper;

    private final TarefaDocumentService tarefaService;

    private final AtividadeDocumentService atividadeDocumentService;

    @GetMapping(value = "/quadro/{quadroId}")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public List<TarefaBasicDTO> findByQuadroId(@PathVariable Integer quadroId) {
        return tarefaService.findByQuadroId(quadroId).stream().map(e -> mapper.map(e, TarefaBasicDTO.class)).toList();
    }

    @GetMapping(value = "/{id}")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public TarefaDTO findById(@PathVariable String id) {
        return mapper.map(tarefaService.findById(new ObjectId(id)), TarefaDTO.class);
    }

    @PostMapping
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<String> save(@RequestBody TarefaBasicDTO tarefaBasicDTO) {
        TarefaDocument tarefaDocument = tarefaService.save(mapper.map(tarefaBasicDTO, TarefaDocument.class));
        return ResponseEntity.ok(tarefaDocument.getId().toString());
    }

    @PostMapping(value = "/{id}/atividade")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<String> save(@PathVariable String id, @RequestBody AtividadeDTO atividadeDTO) {
        TarefaDocument tarefaDocument = tarefaService.findById(new ObjectId(id));
        AtividadeDocument atividadeDocument = mapper.map(atividadeDTO, AtividadeDocument.class);
        atividadeDocument.setTarefa(tarefaDocument);
        tarefaDocument.getAtividades().add(atividadeDocument);
        atividadeDocumentService.save(atividadeDocument);
        tarefaService.save(tarefaDocument);
        return ResponseEntity.ok(atividadeDocument.getId().toString());
    }

    @PutMapping(value = "/{id}/atividade")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<String> updateAtividade(@PathVariable String id,
            @RequestBody AtividadeDTO atividadeDTO) {
        AtividadeDocument noBanco = atividadeDocumentService.findById(new ObjectId(atividadeDTO.getId()));
        mapper.map(atividadeDTO, noBanco);
        noBanco.setId(new ObjectId(atividadeDTO.getId()));
        atividadeDocumentService.save(noBanco);
        return ResponseEntity.ok(noBanco.getId().toString());
    }

    @PutMapping(value = "/atividade/index")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<String> updateAtividadeIndex(@RequestBody List<AtividadeIndexDTO> atividadeDocuments) {
        atividadeDocuments.forEach(atividadeDocumentService::updateIndex);
        return ResponseEntity.ok(ResponseType.SUCESS_SAVE.getMessage());
    }

    @PutMapping
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<String> update(@RequestBody TarefaDTO tarefaDTO) {
        mapper.getConfiguration().setSkipNullEnabled(true);
        TarefaDocument tarefaDocument = mapper.map(tarefaDTO, TarefaDocument.class);
        tarefaDocument.getAtividades().clear();
        tarefaDocument.setAtividades(tarefaDTO.getAtividades().stream().map(e -> {
            AtividadeDocument atividadeDocument = mapper.map(e, AtividadeDocument.class);
            atividadeDocument.setId(new ObjectId(e.getId()));
            return atividadeDocument;
        }).toList());
        tarefaDocument.setId(new ObjectId(tarefaDTO.getId()));
        tarefaService.update(tarefaDocument);
        return ResponseEntity.ok(tarefaDTO.getId());
    }

    @PutMapping(value = "/index")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<String> updateIndex(@RequestBody List<TarefaIndexDTO> tarefaBasicDTOs) {
        tarefaBasicDTOs.forEach(tarefaService::updateIndex);
        return ResponseEntity.ok(ResponseType.SUCESS_SAVE.getMessage());
    }

}

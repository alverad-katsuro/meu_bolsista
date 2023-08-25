package alveradkatsuro.com.br.meu_bolsista.controller.tarefa;

import java.util.List;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.dto.tarefa.AtividadeIndexDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.tarefa.IndicarPesquisadorDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.tarefa.TarefaBasicDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.tarefa.TarefaDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.tarefa.TarefaDTO.AtividadeDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.tarefa.TarefaIndexDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.usuario.UsuarioDTO;
import alveradkatsuro.com.br.meu_bolsista.enumeration.ResponseType;
import alveradkatsuro.com.br.meu_bolsista.model.tarefa.AtividadeDocument;
import alveradkatsuro.com.br.meu_bolsista.model.tarefa.TarefaDocument;
import alveradkatsuro.com.br.meu_bolsista.service.tarefa.AtividadeDocumentService;
import alveradkatsuro.com.br.meu_bolsista.service.tarefa.TarefaDocumentService;
import alveradkatsuro.com.br.meu_bolsista.service.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tarefa")
public class TarefaController {

    private final ModelMapper mapper;

    private final UsuarioService usuarioService;

    private final TarefaDocumentService tarefaService;

    private final AtividadeDocumentService atividadeDocumentService;

    @GetMapping(value = "/quadro/{quadroId}")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public List<TarefaBasicDTO> findByQuadroId(@PathVariable Integer quadroId) {
        return tarefaService.findByQuadroId(quadroId).stream().map(this::convertToBasicDTO).toList();
    }

    @GetMapping(value = "/{id}")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public TarefaDTO findById(@PathVariable String id) {
        return convertToDTO(tarefaService.findById(new ObjectId(id)));
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

    @PostMapping(value = "/{id}/ingressar")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public void ingressarTarefa(@PathVariable String id, JwtAuthenticationToken token) {
        TarefaDocument tarefaDocument = tarefaService.findById(new ObjectId(id));
        tarefaDocument.setResponsavel(token.getName());
        tarefaService.save(tarefaDocument);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PostMapping(value = "/{id}/indicarPesquisadorTarefa")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public void ingressarTarefa(@PathVariable String id, @RequestBody IndicarPesquisadorDTO indicarPesquisadorDTO) {
        TarefaDocument tarefaDocument = tarefaService.findById(new ObjectId(id));
        tarefaDocument.setResponsavel(indicarPesquisadorDTO.getPesquisadorId());
        tarefaService.save(tarefaDocument);
    }

    @PutMapping(value = "/atividade")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<String> updateAtividade(@RequestBody AtividadeDTO atividadeDTO) {
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

    @DeleteMapping(value = "{tarefaId}/atividade/{atividadeId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAtividade(@PathVariable String tarefaId, @PathVariable String atividadeId) {
        tarefaService.removeAtividade(tarefaId, atividadeId);
        atividadeDocumentService.deleteById(atividadeId);
    }

    @PutMapping
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<String> update(@RequestBody TarefaDTO tarefaDTO) {
        mapper.getConfiguration().setSkipNullEnabled(true);
        TarefaDocument tarefaDocument = mapper.map(tarefaDTO, TarefaDocument.class);
        if (tarefaDTO.getResponsavel() != null) {
            tarefaDocument.setResponsavel(tarefaDTO.getResponsavel().getId());
        }
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

    @DeleteMapping(value = "/{id}")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        tarefaService.deleteById(new ObjectId(id));
    }

    private TarefaBasicDTO convertToBasicDTO(TarefaDocument tarefaDocument) {
        var tarefaBasic = mapper.map(tarefaDocument, TarefaBasicDTO.class);
        if (tarefaDocument.getResponsavel() != null) {
            var responsavel = usuarioService.findById(tarefaDocument.getResponsavel());
            tarefaBasic.setResponsavel(mapper.map(responsavel, UsuarioDTO.class));
        }
        return tarefaBasic;
    }

    private TarefaDTO convertToDTO(TarefaDocument tarefaDocument) {
        var tarefa = mapper.map(tarefaDocument, TarefaDTO.class);
        if (tarefaDocument.getResponsavel() != null) {
            var responsavel = usuarioService.findById(tarefaDocument.getResponsavel());
            tarefa.setResponsavel(mapper.map(responsavel, UsuarioDTO.class));
        }
        return tarefa;
    }

}

package alveradkatsuro.com.br.meu_bolsista.controller.tarefa;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.model.tarefa.TarefaDocument;
import alveradkatsuro.com.br.meu_bolsista.service.tarefa.TarefaDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tarefa")
public class TarefaController {

    private final TarefaDocumentService tarefaService;

    @GetMapping(value = "/quadro/{quadroId}")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public List<TarefaDocument> findByQuadroId(@PathVariable Integer quadroId) {
        return tarefaService.findByQuadroId(quadroId);
    }

    @GetMapping(value = "/{id}")
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public TarefaDocument findById(@PathVariable ObjectId id) {
        return tarefaService.findById(id);
    }

    @PostMapping
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<String> save(@RequestBody TarefaDocument tarefaDocument) {
        tarefaDocument = tarefaService.save(tarefaDocument);
        return ResponseEntity.ok(tarefaDocument.getId().toString());
    }

    @PutMapping
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public TarefaDocument update(@RequestBody TarefaDocument tarefaDocument) {
        return tarefaService.save(tarefaDocument);
    }

}

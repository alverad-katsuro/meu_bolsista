package alveradkatsuro.com.br.meu_bolsista.controller.tarefa;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.model.tarefa.TarefaDocument;
import alveradkatsuro.com.br.meu_bolsista.service.tarefa.TarefaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tarefa")
public class TarefaController {

    private final TarefaService tarefaService;

    @GetMapping
    public List<TarefaDocument> findByPlanoTrabalho(Integer planoTrabalhoId) {
        return tarefaService.findByPlanoTrabalho(planoTrabalhoId);
    }

    @GetMapping(value = "/{id}")
    public TarefaDocument findById(@PathVariable ObjectId id) {
        return tarefaService.findById(id);
    }

    @PostMapping
    public TarefaDocument save(@RequestBody TarefaDocument tarefaDocument) {
        return tarefaService.save(tarefaDocument);
    }

    @PutMapping
    public TarefaDocument update(@RequestBody TarefaDocument tarefaDocument) {
        return tarefaService.save(tarefaDocument);
    }

}

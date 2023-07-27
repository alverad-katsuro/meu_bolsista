package alveradkatsuro.com.br.meu_bolsista.service.tarefa;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import alveradkatsuro.com.br.meu_bolsista.model.tarefa.TarefaDocument;
import alveradkatsuro.com.br.meu_bolsista.repository.tarefa.TarefaMongoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaMongoRepository tarefaMongoRepository;

    public List<TarefaDocument> findByQuadroId(Integer quadroId) {
        return tarefaMongoRepository.findByQuadroId(quadroId);
    }

    public TarefaDocument findById(ObjectId id) {
        return tarefaMongoRepository.findById(id).orElseThrow();
    }

    public TarefaDocument save(TarefaDocument tarefaDocument) {
        return tarefaMongoRepository.save(tarefaDocument);
    }

    public List<TarefaDocument> save(List<TarefaDocument> tarefaDocuments) {
        return tarefaMongoRepository.saveAll(tarefaDocuments);
    }

    public TarefaDocument update(TarefaDocument tarefaDocument) {
        return tarefaMongoRepository.save(tarefaDocument);
    }
}

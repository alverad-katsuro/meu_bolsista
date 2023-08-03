package alveradkatsuro.com.br.meu_bolsista.service.tarefa;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import alveradkatsuro.com.br.meu_bolsista.dto.tarefa.TarefaIndexDTO;
import alveradkatsuro.com.br.meu_bolsista.model.tarefa.TarefaDocument;
import alveradkatsuro.com.br.meu_bolsista.repository.tarefa.TarefaMongoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarefaDocumentService {

    private final MongoOperations mongoOperations;

    private final TarefaMongoRepository tarefaMongoRepository;

    public List<TarefaDocument> findByQuadroId(Integer quadroId) {
        return tarefaMongoRepository.findByQuadroId(quadroId);
    }

    public boolean existsByObjetivoId(Integer objetivoId) {
        return tarefaMongoRepository.existsByObjetivoId(objetivoId);
    }

    public TarefaDocument findById(ObjectId id) {
        return tarefaMongoRepository.findById(id).orElseThrow();
    }

    public TarefaDocument findByQuadroIdAndObjetivoId(Integer quadroId, Integer objetivoId) {
        return tarefaMongoRepository.findByQuadroIdAndObjetivoId(quadroId, objetivoId).orElseThrow();
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

    public void updateIndex(TarefaIndexDTO... tarefaIndexDTOs) {
        for (TarefaIndexDTO tarefaIndexDTO : tarefaIndexDTOs) {
            Document document = new Document();
            mongoOperations.getConverter().write(tarefaIndexDTO, document);
            Update update = new Update();
            document.forEach(update::set);
            mongoOperations.findAndModify(
                    Query.query(Criteria.where("_id").is(tarefaIndexDTO.getId())), update, TarefaDocument.class);
        }    }
}

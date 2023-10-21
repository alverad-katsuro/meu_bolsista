package br.com.alverad.meu_bolsista.service.tarefa;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import br.com.alverad.meu_bolsista.dto.tarefa.AtividadeIndexDTO;
import br.com.alverad.meu_bolsista.model.tarefa.AtividadeDocument;
import br.com.alverad.meu_bolsista.repository.tarefa.AtividadeMongoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtividadeDocumentService {

    private final MongoOperations mongoOperations;

    private final AtividadeMongoRepository atividadeMongoRepository;

    public AtividadeDocument findById(ObjectId id) {
        return atividadeMongoRepository.findById(id).orElseThrow();
    }

    public AtividadeDocument save(AtividadeDocument tarefaDocument) {
        return atividadeMongoRepository.save(tarefaDocument);
    }

    public List<AtividadeDocument> save(List<AtividadeDocument> tarefaDocuments) {
        return atividadeMongoRepository.saveAll(tarefaDocuments);
    }

    public AtividadeDocument update(AtividadeDocument tarefaDocument) {
        return atividadeMongoRepository.save(tarefaDocument);
    }

    public void deleteById(ObjectId id) {
        atividadeMongoRepository.deleteById(id);
    }

    public void deleteById(String id) {
        this.deleteById(new ObjectId(id));
    }

    public void updateIndex(AtividadeIndexDTO... atividadeIndexDTOs) {
        for (AtividadeIndexDTO atividadeIndexDTO : atividadeIndexDTOs) {
            Document document = new Document();
            mongoOperations.getConverter().write(atividadeIndexDTO, document);
            Update update = new Update();
            document.forEach(update::set);
            mongoOperations.findAndModify(
                    Query.query(Criteria.where("_id").is(atividadeIndexDTO.getId())), update, AtividadeDocument.class);
        }
    }
}

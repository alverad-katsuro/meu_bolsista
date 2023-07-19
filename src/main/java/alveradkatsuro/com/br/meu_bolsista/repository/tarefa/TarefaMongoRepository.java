package alveradkatsuro.com.br.meu_bolsista.repository.tarefa;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import alveradkatsuro.com.br.meu_bolsista.model.tarefa.TarefaDocument;
import java.util.List;


public interface TarefaMongoRepository extends MongoRepository<TarefaDocument, ObjectId> {

    List<TarefaDocument> findByPlanoTrabalho(Integer planoTrabalho);
}

package br.com.alverad.meu_bolsista.repository.tarefa;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.alverad.meu_bolsista.model.tarefa.TarefaDocument;


public interface TarefaMongoRepository extends MongoRepository<TarefaDocument, ObjectId> {

    List<TarefaDocument> findByQuadroId(Integer quadroId);

    Optional<TarefaDocument> findByQuadroIdAndObjetivoId(Integer quadroId, Integer objetivoId);

    boolean existsByObjetivoId(Integer objetivoId);



}

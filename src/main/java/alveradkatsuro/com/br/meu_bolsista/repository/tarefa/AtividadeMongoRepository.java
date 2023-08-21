package alveradkatsuro.com.br.meu_bolsista.repository.tarefa;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import alveradkatsuro.com.br.meu_bolsista.model.tarefa.AtividadeDocument;

public interface AtividadeMongoRepository extends MongoRepository<AtividadeDocument, ObjectId> {

}

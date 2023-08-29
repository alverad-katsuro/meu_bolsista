package alveradkatsuro.com.br.meu_bolsista.model.tarefa;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "impedimento")
public class ImpedimentoDocument {

    @Id
    private ObjectId id = new ObjectId();

    private String impedimento;

    private LocalDate dataOcorrido;

    private Integer index;

    @DBRef(lazy = true)
    private TarefaDocument tarefa;

}
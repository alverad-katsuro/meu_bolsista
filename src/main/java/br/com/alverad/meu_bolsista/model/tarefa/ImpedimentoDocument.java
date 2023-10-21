package br.com.alverad.meu_bolsista.model.tarefa;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @Document(value = "impedimento")
public class ImpedimentoDocument {

    // @Id
    // private ObjectId id = new ObjectId();

    private String impedimento;

    private LocalDate dataOcorrido;

    private Integer index;

    // @DBRef(lazy = true)
    // private TarefaDocument tarefa;

}
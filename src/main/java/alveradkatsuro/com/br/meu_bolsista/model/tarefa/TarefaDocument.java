package alveradkatsuro.com.br.meu_bolsista.model.tarefa;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import alveradkatsuro.com.br.meu_bolsista.enumeration.ColunaKanban;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class TarefaDocument {

    @Id
    private ObjectId id;

    @DBRef(lazy = true)
    private TarefaDocument pai;

    private LocalDateTime fim;

    private LocalDateTime inicio;

    private String titulo;

    private String descricao;

    private ColunaKanban colunaKanban;

    private Integer posicaoKanban;

    private Boolean concluida;

    private Integer cargaHoraria;

    private List<String> etiquetas;

    private Integer responsavel;

    private List<Integer> resultadosObtidos;

    private List<AtividadeDocument> atividades;

    private List<ImpedimentoDocument> impedimentos;

    private Integer planoTrabalho;

}

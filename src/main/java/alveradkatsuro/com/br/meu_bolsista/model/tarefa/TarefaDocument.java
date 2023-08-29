package alveradkatsuro.com.br.meu_bolsista.model.tarefa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import alveradkatsuro.com.br.meu_bolsista.enumeration.ColunaKanban;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "tarefa_document")
public class TarefaDocument {

    @Id
    private ObjectId id;

    private String titulo;

    private String descricao;

    @Builder.Default
    private ColunaKanban colunaKanban = ColunaKanban.TODO;

    private LocalDateTime fim;

    private LocalDateTime inicio;

    private Integer quadroId;

    private Integer posicaoKanban;

    @DBRef(lazy = true)
    private TarefaDocument pai;

    private Integer objetivoId;

    private Boolean concluida;

    private Integer cargaHoraria;

    private List<String> etiquetas;

    private String responsavel;

    private List<Integer> resultadosObtidos;

    @Builder.Default
    @DBRef(lazy = false)
    private List<AtividadeDocument> atividades = new ArrayList<>(0);

    @Builder.Default
    @DBRef(lazy = false)
    private List<ImpedimentoDocument> impedimentos = new ArrayList<>(0);

}

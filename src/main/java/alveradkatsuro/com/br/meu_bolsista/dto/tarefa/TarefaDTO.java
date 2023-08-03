package alveradkatsuro.com.br.meu_bolsista.dto.tarefa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

import alveradkatsuro.com.br.meu_bolsista.enumeration.ColunaKanban;
import alveradkatsuro.com.br.meu_bolsista.model.tarefa.TarefaDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarefaDTO {

    private String id;

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

    private Integer responsavel;

    private List<Integer> resultadosObtidos;

    private List<AtividadeDocument> atividades;

    private List<ImpedimentoDocument> impedimentos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class AtividadeDocument {

        private String atividade;

        private Boolean concluida;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ImpedimentoDocument {

        private String impedimento;

        private LocalDate dataOcorrido;
    }
}
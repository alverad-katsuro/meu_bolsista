package alveradkatsuro.com.br.meu_bolsista.dto.tarefa;

import alveradkatsuro.com.br.meu_bolsista.enumeration.ColunaKanban;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarefaIndexDTO {

    private String id;

    @Builder.Default
    private ColunaKanban colunaKanban = ColunaKanban.TODO;

    private Integer posicaoKanban;

}
package alveradkatsuro.com.br.meu_bolsista.dto.tarefa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtividadeIndexDTO {

    private String id;

    private Integer index;

}
package br.com.alverad.meu_bolsista.dto.objetivo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjetivoDTO {

    private Integer id;

    private String descricao;

}

package alveradkatsuro.com.br.meu_bolsista.dto.processo_seletivo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessoSeletivoDTO {

    private Integer id;

    private String areaInteresse;

    private String requisitos;

    private LocalDate inicio;

    private LocalDate fim;

}

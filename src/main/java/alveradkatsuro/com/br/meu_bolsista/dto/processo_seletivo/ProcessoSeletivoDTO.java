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

    private LocalDate fim;

    private LocalDate inicio;

    private String requisitos;

    private String areaInteresse;

    private Integer planoTrabalhoId;

}

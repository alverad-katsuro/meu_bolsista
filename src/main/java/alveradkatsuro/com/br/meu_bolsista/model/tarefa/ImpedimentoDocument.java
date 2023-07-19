package alveradkatsuro.com.br.meu_bolsista.model.tarefa;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImpedimentoDocument {

    private String impedimento;

    private LocalDate dataOcorrido;
}

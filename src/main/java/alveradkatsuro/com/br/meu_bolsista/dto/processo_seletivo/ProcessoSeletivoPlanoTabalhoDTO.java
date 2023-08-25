package alveradkatsuro.com.br.meu_bolsista.dto.processo_seletivo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProcessoSeletivoPlanoTabalhoDTO {

    private Integer id;

    private String titulo;

    private String capaUrl;

    private String capaResourceId;

    public ProcessoSeletivoPlanoTabalhoDTO(Integer id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

}

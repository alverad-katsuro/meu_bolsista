package alveradkatsuro.com.br.meu_bolsista.dto.usuario_plano_trabalho;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioNovoPlanoDTO {

    private String id;

    private String nome;

    private int cargaHoraria;

    private Boolean participante;

    private Integer planoTrabalhoId;

}

package alveradkatsuro.com.br.meu_bolsista.dto.usuario_plano_trabalho;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPlanoTrabalhoCreateDTO {

    String id;

    String nome;

    Integer cargaHoraria;

    Boolean participante;

    Integer planoTrabalhoId;

}

package alveradkatsuro.com.br.meu_bolsista.dto.usuario_plano_trabalho;

import alveradkatsuro.com.br.meu_bolsista.dto.usuario.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPlanoTrabalhoDTO {

    Integer cargaHoraria;

    UsuarioDTO usuario;

}

package br.com.alverad.meu_bolsista.dto.usuario_plano_trabalho;

import br.com.alverad.meu_bolsista.dto.usuario.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPlanoDTO {

    private Integer cargaHoraria;

    private UsuarioDTO usuario;

}

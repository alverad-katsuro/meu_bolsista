package br.com.alverad.meu_bolsista.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioBasicDTO {

    private String id;

    private String nome;

    private String email;

}

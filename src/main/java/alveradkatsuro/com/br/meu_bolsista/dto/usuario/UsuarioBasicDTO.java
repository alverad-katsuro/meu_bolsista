package alveradkatsuro.com.br.meu_bolsista.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioBasicDTO {

    private String id;

    private String nome;

    private String email;

}

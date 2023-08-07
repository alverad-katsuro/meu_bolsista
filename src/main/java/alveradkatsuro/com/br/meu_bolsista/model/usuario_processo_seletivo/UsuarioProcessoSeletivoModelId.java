package alveradkatsuro.com.br.meu_bolsista.model.usuario_processo_seletivo;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioProcessoSeletivoModelId implements Serializable {

    private String usuarioId;

    private Integer processoSeletivoId;
}

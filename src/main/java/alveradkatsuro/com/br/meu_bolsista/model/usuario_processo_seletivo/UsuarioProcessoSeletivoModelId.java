package alveradkatsuro.com.br.meu_bolsista.model.usuario_processo_seletivo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class UsuarioProcessoSeletivoModelId implements Serializable {

    @Column(name = "usuario_id", unique = false, nullable = false)
    private Integer usuarioId;

    @Column(name = "processo_seletivo_id", unique = false, nullable = false)
    private Integer processoSeletivoId;
}

package alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class UsuarioPlanoTrabalhoModelId {

    @Column(name = "usuario_id", unique = false, nullable = false)
    private Integer usuarioId;

    @Column(name = "plano_trabalho_id", unique = false, nullable = false)
    private Integer planoTrabalhoId;
}

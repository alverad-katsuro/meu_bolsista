package alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPlanoTrabalhoModelId {

    @Column(name = "id_usuario", unique = false, nullable = false)
    private String usuarioId;

    @Column(name = "id_plano_trabalho", unique = false, nullable = false)
    private Integer planoTrabalhoId;
}

package br.com.alverad.meu_bolsista.model.usuario_plano_trabalho;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPlanoTrabalhoModelId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_usuario", unique = false, nullable = false)
    private String usuarioId;

    @Column(name = "id_plano_trabalho", unique = false, nullable = false)
    private Integer planoTrabalhoId;
}

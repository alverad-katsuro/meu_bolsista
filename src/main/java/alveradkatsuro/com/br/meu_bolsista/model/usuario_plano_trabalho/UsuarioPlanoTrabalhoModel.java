package alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho;

import java.io.Serializable;

import alveradkatsuro.com.br.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "usuario_plano_trabalho")
@EqualsAndHashCode(exclude = { "planoTrabalho" })
public class UsuarioPlanoTrabalhoModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @Builder.Default
    private UsuarioPlanoTrabalhoModelId id = new UsuarioPlanoTrabalhoModelId();

    @Column(name = "carga_horaria_usuario_plano", unique = false, nullable = false)
    private Integer cargaHoraria;

    @MapsId(value = "planoTrabalhoId")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlanoTrabalhoModel planoTrabalho;
}

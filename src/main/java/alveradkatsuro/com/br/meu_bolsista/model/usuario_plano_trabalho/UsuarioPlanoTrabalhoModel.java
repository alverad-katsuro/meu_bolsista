package alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho;

import alveradkatsuro.com.br.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
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
@EqualsAndHashCode(exclude = { "usuario", "planoTrabalho" })
public class UsuarioPlanoTrabalhoModel {

    @EmbeddedId
    private UsuarioPlanoTrabalhoModelId id;

    @Column(name = "carga_horaria_usuario_plano", unique = false, nullable = false)
    private Integer cargaHoraria;

    @MapsId(value = "usuarioId")
    @ManyToOne(fetch = FetchType.LAZY)
    private UsuarioModel usuario;

    @MapsId(value = "planoTrabalhoId")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlanoTrabalhoModel planoTrabalho;
}

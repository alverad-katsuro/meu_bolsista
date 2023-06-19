package alveradkatsuro.com.br.meu_bolsista.model.usuario_processo_seletivo;

import java.time.LocalDate;

import alveradkatsuro.com.br.meu_bolsista.model.processo_seletivo.ProcessoSeletivoModel;
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
@Entity(name = "usuario_processo_seletivo")
@EqualsAndHashCode(exclude = { "usuario", "processoSeletivo" })
public class UsuarioProcessoSeletivoModel {

    @EmbeddedId
    private UsuarioProcessoSeletivoModelId id;

    @Column(name = "aprovado_usuario_processo", unique = false, nullable = false)
    private Boolean aprovado;

    @Column(name = "inscricao_usuario_processo", unique = false, nullable = false)
    private LocalDate inscricao;

    @Column(name = "curriculo_usuario_processo", unique = true, nullable = false)
    private String curriculo;

    @MapsId(value = "usuarioId")
    @ManyToOne(fetch = FetchType.LAZY)
    private UsuarioModel usuario;

    @MapsId(value = "processoSeletivoId")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProcessoSeletivoModel processoSeletivo;
}

package alveradkatsuro.com.br.meu_bolsista.model.usuario_processo_seletivo;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import alveradkatsuro.com.br.meu_bolsista.model.audit.Auditable;
import alveradkatsuro.com.br.meu_bolsista.model.processo_seletivo.ProcessoSeletivoModel;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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
@EqualsAndHashCode(exclude = { "processoSeletivo" }, callSuper = false)
public class UsuarioProcessoSeletivoModel extends Auditable implements Serializable {

    @EmbeddedId
    @Builder.Default
    private UsuarioProcessoSeletivoModelId id = new UsuarioProcessoSeletivoModelId();

    @ColumnDefault(value = "false")
    @Column(name = "aprovado_usuario_processo", unique = false, nullable = false)
    private Boolean aprovado;

    @CreatedDate
    @ColumnDefault(value = "now()")
    @Column(name = "inscricao_usuario_processo", unique = false)
    private LocalDateTime inscricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "processoSeletivoId")
    @JoinColumn(name = "processo_seletivo_id", unique = false, nullable = false)
    private ProcessoSeletivoModel processoSeletivo;
}

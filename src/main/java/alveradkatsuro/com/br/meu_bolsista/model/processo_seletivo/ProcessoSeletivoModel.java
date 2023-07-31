package alveradkatsuro.com.br.meu_bolsista.model.processo_seletivo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import alveradkatsuro.com.br.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "processo_seletivo")
@EqualsAndHashCode(callSuper = false, exclude = "candidatos")
public class ProcessoSeletivoModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_processo_seletivo", unique = true, nullable = false)
    private Integer id;

    @Column(name = "area_interesse_processo_seletivo", unique = false, nullable = false)
    private String areaInteresse;

    @Lob
    @Column(name = "requisitos_processo_seletivo", unique = false, nullable = false)
    private String requisitos;

    @Column(name = "inicio_processo_seletivo", unique = false, nullable = false)
    private LocalDate inicio;

    @Column(name = "fim_processo_seletivo", unique = false, nullable = false)
    private LocalDate fim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_trabalho", unique = false, nullable = false)
    private PlanoTrabalhoModel planoTrabalho;

    @OneToMany(mappedBy = "processoSeletivo", cascade = {
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE }, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UsuarioProcessoSeletivoModel> candidatos;
}

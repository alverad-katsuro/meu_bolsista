package alveradkatsuro.com.br.meu_bolsista.model.plano_trabalho;

import java.util.Set;

import alveradkatsuro.com.br.meu_bolsista.model.audit.Auditable;
import alveradkatsuro.com.br.meu_bolsista.model.processo_seletivo.ProcessoSeletivoModel;
import alveradkatsuro.com.br.meu_bolsista.model.recurso_material.RecursoMaterialModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
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
@Entity(name = "plano_trabalho")
@EqualsAndHashCode(callSuper = false, exclude = "lider")
public class PlanoTrabalhoModel extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plano_trabalho", unique = true, nullable = false)
    private Integer id;

    @Column(name = "titulo_plano_trabalho", unique = true, nullable = false)
    private String titulo;

    @Column(name = "area_trabalho_plano_trabalho", unique = true, nullable = false)
    private String areaAtra;

    @Lob
    @Column(name = "descricao_plano_trabalho", unique = true, nullable = false)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lider", unique = false, nullable = false)
    private UsuarioModel lider;

    @OneToMany(mappedBy = "planoTrabalho", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProcessoSeletivoModel> processoSeletivos;

    @OneToMany(mappedBy = "planoTrabalho", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecursoMaterialModel> recursoMateriais;
}
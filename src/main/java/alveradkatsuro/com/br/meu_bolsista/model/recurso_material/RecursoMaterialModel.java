package alveradkatsuro.com.br.meu_bolsista.model.recurso_material;

import java.io.Serializable;

import alveradkatsuro.com.br.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "recurso_material")
@EqualsAndHashCode(callSuper = false, exclude = "planoTrabalho")
public class RecursoMaterialModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recurso_material", unique = true, nullable = false)
    private Integer id;

    @Column(name = "descricao_recurso_material", unique = false, nullable = false)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_trabalho_recurso_material", unique = true, nullable = false)
    private PlanoTrabalhoModel planoTrabalho;
}

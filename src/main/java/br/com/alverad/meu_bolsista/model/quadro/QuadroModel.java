package br.com.alverad.meu_bolsista.model.quadro;

import java.io.Serializable;

import org.hibernate.annotations.ColumnDefault;

import br.com.alverad.meu_bolsista.model.audit.Auditable;
import br.com.alverad.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "quadro")
@EqualsAndHashCode(callSuper = false, exclude = "planoTrabalho")
public class QuadroModel extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quadro", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_trabalho", unique = true, nullable = false)
    private PlanoTrabalhoModel planoTrabalho;

    @ColumnDefault(value = "0")
    @Column(name = "quantidade_tarefas", unique = false, nullable = false)
    private int quantidadeTarefas;

    @ColumnDefault(value = "0")
    @Column(name = "tarefas_com_atraso", unique = false, nullable = false)
    private int tarefasConcluidasAtrasadas;

    @ColumnDefault(value = "0")
    @Column(name = "tarefas_no_prazo", unique = false, nullable = false)
    private int tarefasConcluidasNoPrazo;

}

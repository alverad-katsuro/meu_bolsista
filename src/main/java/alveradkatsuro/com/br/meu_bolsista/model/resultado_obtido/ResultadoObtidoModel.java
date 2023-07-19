package alveradkatsuro.com.br.meu_bolsista.model.resultado_obtido;

import alveradkatsuro.com.br.meu_bolsista.model.resultado_esperado.ResultadoEsperadoModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "resultado_obtido")
public class ResultadoObtidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado_obtido", unique = true, nullable = false)
    private Integer id;

    @Column(name = "descricao_resultado_obtido", unique = false, nullable = false, length = 254)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_resultado_esperado", unique = false, nullable = true)
    private ResultadoEsperadoModel resultadoEsperado;

}

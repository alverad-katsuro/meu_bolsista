package br.com.alverad.meu_bolsista.model.resultado_esperado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "resultado_esperado")
public class ResultadoEsperadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado_esperado", unique = true, nullable = false)
    private Integer id;

    @Column(name = "descricao_resultado_esperado", unique = false, nullable = false, length = 254)
    private String descricao;

}

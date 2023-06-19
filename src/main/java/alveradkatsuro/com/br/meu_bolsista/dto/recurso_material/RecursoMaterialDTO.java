package alveradkatsuro.com.br.meu_bolsista.dto.recurso_material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecursoMaterialDTO {

    private Integer id;

    private String descricao;

}

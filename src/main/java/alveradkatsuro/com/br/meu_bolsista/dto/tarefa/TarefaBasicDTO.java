package alveradkatsuro.com.br.meu_bolsista.dto.tarefa;

import java.time.LocalDateTime;
import java.util.List;

import alveradkatsuro.com.br.meu_bolsista.dto.usuario.UsuarioDTO;
import alveradkatsuro.com.br.meu_bolsista.enumeration.ColunaKanban;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarefaBasicDTO {

    private String id;

    private String titulo;

    @Builder.Default
    private ColunaKanban colunaKanban = ColunaKanban.TODO;

    private LocalDateTime fim;

    private LocalDateTime inicio;

    private Integer quadroId;

    private Integer posicaoKanban;

    private List<String> etiquetas;

    private UsuarioDTO responsavel;

}
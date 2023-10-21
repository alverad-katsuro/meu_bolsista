package br.com.alverad.meu_bolsista.dto.tarefa;

import java.time.LocalDateTime;
import java.util.List;

import br.com.alverad.meu_bolsista.dto.tarefa.TarefaDTO.EtiquetaDTO;
import br.com.alverad.meu_bolsista.dto.usuario.UsuarioDTO;
import br.com.alverad.meu_bolsista.enumeration.ColunaKanban;
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

    private List<EtiquetaDTO> etiquetas;

    private UsuarioDTO responsavel;

}
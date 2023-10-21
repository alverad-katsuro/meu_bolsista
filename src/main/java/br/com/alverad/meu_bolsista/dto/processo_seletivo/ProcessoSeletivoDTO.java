package br.com.alverad.meu_bolsista.dto.processo_seletivo;

import java.time.LocalDate;
import java.util.Set;

import br.com.alverad.meu_bolsista.dto.usuario_processo_seletivo.UsuarioProcessoSeletivoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessoSeletivoDTO {

    private Integer id;

    private LocalDate fim;

    private LocalDate inicio;

    private boolean inscrito;

    private String requisitos;

    private String areaInteresse;

    private Set<UsuarioProcessoSeletivoDTO> candidatos;

    private ProcessoSeletivoPlanoTabalhoDTO planoTrabalho;

}

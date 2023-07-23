package alveradkatsuro.com.br.meu_bolsista.dto.plano_trabalho;

import java.util.Set;

import alveradkatsuro.com.br.meu_bolsista.dto.objetivo.ObjetivoDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.processo_seletivo.ProcessoSeletivoDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.recurso_material.RecursoMaterialDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanoTrabalhoDTO {

    private Integer id;

    private String titulo;

    private String areaTrabalho;

    private String descricao;

    private String liderNome;

    private Set<ProcessoSeletivoDTO> processoSeletivos;

    private Set<RecursoMaterialDTO> recursoMateriais;

    private Set<ObjetivoDTO> objetivos;

}

package br.com.alverad.meu_bolsista.dto.plano_trabalho;

import java.util.Set;

import br.com.alverad.meu_bolsista.dto.objetivo.ObjetivoDTO;
import br.com.alverad.meu_bolsista.dto.processo_seletivo.ProcessoSeletivoDTO;
import br.com.alverad.meu_bolsista.dto.recurso_material.RecursoMaterialDTO;
import br.com.alverad.meu_bolsista.dto.usuario_plano_trabalho.UsuarioPlanoTrabalhoCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanoTrabalhoCreateDTO {

    private Integer id;

    private String titulo;

    private String areaTrabalho;

    private String descricao;

    private String capaResourceId;

    private Set<ObjetivoDTO> objetivos;

    private Set<ProcessoSeletivoDTO> processoSeletivos;

    private Set<RecursoMaterialDTO> recursoMateriais;

    private Set<UsuarioPlanoTrabalhoCreateDTO> pesquisadores;

}

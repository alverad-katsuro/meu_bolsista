package alveradkatsuro.com.br.meu_bolsista.dto.plano_trabalho;

import java.util.Set;

import alveradkatsuro.com.br.meu_bolsista.dto.objetivo.ObjetivoDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.processo_seletivo.ProcessoSeletivoDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.recurso_material.RecursoMaterialDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.usuario_plano_trabalho.UsuarioPlanoTrabalhoCreateDTO;
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

    private String liderNome;

    private String capaResourceId;

    private Set<ObjetivoDTO> objetivos;

    private Set<ProcessoSeletivoDTO> processoSeletivos;

    private Set<RecursoMaterialDTO> recursoMateriais;

    private Set<UsuarioPlanoTrabalhoCreateDTO> pesquisadores;

}

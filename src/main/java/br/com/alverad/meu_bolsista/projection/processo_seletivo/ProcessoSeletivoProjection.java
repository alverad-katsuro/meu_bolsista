package br.com.alverad.meu_bolsista.projection.processo_seletivo;

import java.time.LocalDate;
import java.util.Set;

import br.com.alverad.meu_bolsista.projection.usuario_processo_seletivo.UsuarioProcessoSeletivoProjection;

public interface ProcessoSeletivoProjection {

    public Integer getId();

    public LocalDate getFim();

    public LocalDate getInicio();

    public String getRequisitos();

    public String getAreaInteresse();

    public Set<UsuarioProcessoSeletivoProjection> getCandidatos();

    public ProcessoSeletivoPlanoTabalhoProjection getPlanoTrabalho();

    public interface ProcessoSeletivoPlanoTabalhoProjection {

        public Integer getId();

        public String getTitulo();

        public String getCapaResourceId();

    }

}

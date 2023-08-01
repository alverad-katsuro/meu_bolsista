package alveradkatsuro.com.br.meu_bolsista.projection.usuario_processo_seletivo;

import java.time.LocalDateTime;

import alveradkatsuro.com.br.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModelId;

public interface UsuarioProcessoSeletivoProjection {

    public UsuarioProcessoSeletivoModelId getId();

    public Boolean getAprovado();

    public LocalDateTime getInscricao();

    public String getCurriculo();

    public UsuarioBasicProjection getUsuario();

    public interface UsuarioBasicProjection {

        public Integer getId();

        public String getNome();

        public String getEmail();

    }

}

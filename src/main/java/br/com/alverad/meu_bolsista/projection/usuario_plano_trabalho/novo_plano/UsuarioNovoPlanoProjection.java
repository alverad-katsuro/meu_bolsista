package br.com.alverad.meu_bolsista.projection.usuario_plano_trabalho.novo_plano;

public interface UsuarioNovoPlanoProjection {

    String getId(); // usuario ID

    String getNome(); // Nome do usuario

    int getCargaHoraria();

    Boolean getParticipante();

    Integer getPlanoTrabalhoId();

}

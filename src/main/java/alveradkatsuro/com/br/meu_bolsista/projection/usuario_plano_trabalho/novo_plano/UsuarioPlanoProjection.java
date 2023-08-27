package alveradkatsuro.com.br.meu_bolsista.projection.usuario_plano_trabalho.novo_plano;

public interface UsuarioPlanoProjection {

    Integer getCargaHoraria();

    UsuarioPlanoIdProjection getId();

    interface UsuarioPlanoIdProjection {

        String getUsuarioId();

    }

}

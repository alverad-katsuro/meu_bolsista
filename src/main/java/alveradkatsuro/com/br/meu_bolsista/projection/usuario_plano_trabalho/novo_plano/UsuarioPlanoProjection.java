package alveradkatsuro.com.br.meu_bolsista.projection.usuario_plano_trabalho.novo_plano;

public interface UsuarioPlanoProjection {

    Integer getCargaHoraria();

    UsuarioProjection getUsuario();

    interface UsuarioProjection {

        String getId();

        String geEmail();

        String getNome();

        String getLattes();

        String getImagemUrl();

    }

}

package alveradkatsuro.com.br.meu_bolsista.projection.quadro.panel;

public interface QuadroPainelProjection {

    Integer getId();

    String getTitulo();

    PlanoTrabalhoPainelProjection getPlanoTrabalho();

    interface PlanoTrabalhoPainelProjection {
        String getTitulo();
    }
}

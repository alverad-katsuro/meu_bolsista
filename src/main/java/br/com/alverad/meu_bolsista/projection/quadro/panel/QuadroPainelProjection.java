package br.com.alverad.meu_bolsista.projection.quadro.panel;

public interface QuadroPainelProjection {

    Integer getId();

    String getTitulo();

    PlanoTrabalhoPainelProjection getPlanoTrabalho();

    interface PlanoTrabalhoPainelProjection {
        String getTitulo();
    }
}

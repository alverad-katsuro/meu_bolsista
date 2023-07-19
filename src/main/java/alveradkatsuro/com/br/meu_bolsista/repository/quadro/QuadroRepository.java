package alveradkatsuro.com.br.meu_bolsista.repository.quadro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import alveradkatsuro.com.br.meu_bolsista.model.quadro.QuadroModel;
import alveradkatsuro.com.br.meu_bolsista.projection.quadro.panel.QuadroPainelProjection;

public interface QuadroRepository extends PagingAndSortingRepository<QuadroModel, Integer> {

    @Query(value = "select q.id as id, pt.titulo as titulo from quadro q inner join plano_trabalho pt on q.planoTrabalho.id = pt.id")
    Page<QuadroPainelProjection> findAllPainelProjection(Pageable pageable);


}
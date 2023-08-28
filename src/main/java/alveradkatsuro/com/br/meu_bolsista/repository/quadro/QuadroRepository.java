package alveradkatsuro.com.br.meu_bolsista.repository.quadro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import alveradkatsuro.com.br.meu_bolsista.model.quadro.QuadroModel;
import alveradkatsuro.com.br.meu_bolsista.projection.quadro.panel.QuadroPainelProjection;

public interface QuadroRepository extends PagingAndSortingRepository<QuadroModel, Integer> {

    @Query(value = "select q.id as id, pt.titulo as titulo from quadro q inner join plano_trabalho pt on q.planoTrabalho.id = pt.id")
    Page<QuadroPainelProjection> findAllPainelProjection(Pageable pageable);

    @Query(value = "select " +
            "  exists( " +
            "  select " +
            "    1 " +
            "  from " +
            "    quadro q " +
            "  inner join plano_trabalho pt on " +
            "    pt.id = q.planoTrabalho.id " +
            "  inner join usuario_plano_trabalho upt on " +
            "    upt.id.planoTrabalhoId = pt.id " +
            "    and upt.id.usuarioId = :usuarioId " +
            "  where " +
            "    q.id = :quadroId " +
            ") ")
    boolean pesquisadorNoQuadro(String usuarioId, Integer quadroId);

    @Query(value = "select " +
            "  q.id as id,  " +
            "  pt.titulo as titulo " +
            "from " +
            "  quadro q " +
            "inner join plano_trabalho pt on " +
            "  q.planoTrabalho.id = pt.id " +
            "inner join usuario_plano_trabalho upt on " +
            "  pt.id = upt.id.planoTrabalhoId " +
            "  and upt.id.usuarioId = :usuarioId " +
            "where pt.finalizado = false")
    Page<QuadroPainelProjection> findAllForPanelAndUserIn(String usuarioId, PageRequest of);

}

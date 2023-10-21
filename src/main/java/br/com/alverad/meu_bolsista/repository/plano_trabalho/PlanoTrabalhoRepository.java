package br.com.alverad.meu_bolsista.repository.plano_trabalho;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.alverad.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;

public interface PlanoTrabalhoRepository extends CrudRepository<PlanoTrabalhoModel, Integer> {

    Page<PlanoTrabalhoModel> findAll(Pageable page);

    <T> Page<T> findBy(Pageable page, Class<T> tipo);

    @Modifying
    @Query(value = "UPDATE plano_trabalho pt SET pt.finalizado = :isFinalizado WHERE pt.id = :planoTrabalhoId")
    void updateFinalizar(boolean isFinalizado, Integer planoTrabalhoId);

    boolean existsByIdAndRelatorioResourceIdIsNotNull(Integer planoTrabalhoId);

    @Query(value = "select " +
            "  exists ( " +
            "  select " +
            "    pt.id " +
            "  from " +
            "    plano_trabalho pt " +
            "  inner join usuario_plano_trabalho upt on " +
            "    upt.id.planoTrabalhoId = pt.id " +
            "    and upt.id.usuarioId = :usuarioId " +
            "  where pt.id = :planoTrabalhoId" +
            ") ")
    boolean existsByIdAndPesquisadoresId(Integer planoTrabalhoId, String usuarioId);

    @Modifying
    @Query(value = "UPDATE plano_trabalho pt SET pt.relatorioResourceId = :relatorioResourceId WHERE pt.id = :planoTrabalhoId")
    void setRelatorioResourceId(Integer planoTrabalhoId, String relatorioResourceId);

}

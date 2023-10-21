package br.com.alverad.meu_bolsista.repository.usuario_processo_seletivo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.alverad.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModel;
import br.com.alverad.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModelId;

public interface UsuarioProcessoSeletivoRepository
        extends CrudRepository<UsuarioProcessoSeletivoModel, UsuarioProcessoSeletivoModelId> {

    List<UsuarioProcessoSeletivoModel> findAll();

    Page<UsuarioProcessoSeletivoModel> findAll(Pageable pageable);

    @Query("SELECT ps FROM usuario_processo_seletivo ps")
    <T> Page<T> findAll(Pageable pageable, Class<T> type);

    <T> Optional<T> findById(UsuarioProcessoSeletivoModelId id, Class<T> tipo);

    @Query(value = "select Date(ps.fim) >= Date(now()) as noPrazo from processo_seletivo ps where ps.id = :planoTrabalhoId")
    boolean inscricaoNoPrazo(Integer planoTrabalhoId);

    boolean existsByIdProcessoSeletivoIdAndIdUsuarioId(Integer processoSeletivoId, String usuarioId);

}

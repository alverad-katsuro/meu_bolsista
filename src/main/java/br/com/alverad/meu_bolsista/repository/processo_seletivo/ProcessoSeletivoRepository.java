package br.com.alverad.meu_bolsista.repository.processo_seletivo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alverad.meu_bolsista.dto.processo_seletivo.ProcessoSeletivoPlanoTabalhoDTO;
import br.com.alverad.meu_bolsista.model.processo_seletivo.ProcessoSeletivoModel;

public interface ProcessoSeletivoRepository extends JpaRepository<ProcessoSeletivoModel, Integer> {

    <T> Optional<T> findById(Integer id, Class<T> type);

    List<ProcessoSeletivoModel> findAll();

    @Query("SELECT ps FROM processo_seletivo ps")
    <T> Page<T> findAll(Pageable pageable, Class<T> type);

    @Query(value = "select " +
            " new br.com.alverad.meu_bolsista.dto.processo_seletivo.ProcessoSeletivoPlanoTabalhoDTO(pt.id, " +
            " pt.titulo) " +
            "from " +
            " plano_trabalho pt " +
            "left join processo_seletivo ps on " +
            " ps.id = pt.id " +
            "and Date(ps.fim) >= Date(now()) " +
            "where ps.id is null")
    List<ProcessoSeletivoPlanoTabalhoDTO> findProcessosDisponiveis();

}

package alveradkatsuro.com.br.meu_bolsista.repository.processo_seletivo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import alveradkatsuro.com.br.meu_bolsista.model.processo_seletivo.ProcessoSeletivoModel;

public interface ProcessoSeletivoRepository extends CrudRepository<ProcessoSeletivoModel, Integer> {

    List<ProcessoSeletivoModel> findAll();

    Page<ProcessoSeletivoModel> findAll(Pageable pageable);

}

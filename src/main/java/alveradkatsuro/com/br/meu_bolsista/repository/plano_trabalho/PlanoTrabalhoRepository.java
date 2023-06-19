package alveradkatsuro.com.br.meu_bolsista.repository.plano_trabalho;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import alveradkatsuro.com.br.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;

public interface PlanoTrabalhoRepository extends CrudRepository<PlanoTrabalhoModel, Integer> {

    Page<PlanoTrabalhoModel> findAll(Pageable page);
}

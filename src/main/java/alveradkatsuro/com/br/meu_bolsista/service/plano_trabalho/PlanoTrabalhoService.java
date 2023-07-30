package alveradkatsuro.com.br.meu_bolsista.service.plano_trabalho;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;

public interface PlanoTrabalhoService {

    public PlanoTrabalhoModel findById(Integer id);

    public Page<PlanoTrabalhoModel> findAll(Integer page, Integer size, Direction direction);

    public PlanoTrabalhoModel save(PlanoTrabalhoModel planoTrabalho);

    public PlanoTrabalhoModel update(PlanoTrabalhoModel planoTrabalho) throws NotFoundException ;

    public void deleteById(Integer id);

}

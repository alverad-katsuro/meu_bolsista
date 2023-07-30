package alveradkatsuro.com.br.meu_bolsista.service.plano_trabalho.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;
import alveradkatsuro.com.br.meu_bolsista.repository.plano_trabalho.PlanoTrabalhoRepository;
import alveradkatsuro.com.br.meu_bolsista.service.plano_trabalho.PlanoTrabalhoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanoTrabalhoServiceImpl implements PlanoTrabalhoService {

    private final PlanoTrabalhoRepository planoTrabalhoRepository;

    @Override
    public void deleteById(Integer id) {
        planoTrabalhoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Page<PlanoTrabalhoModel> findAll(Integer page, Integer size, Direction direction) {
        return planoTrabalhoRepository.findBy(PageRequest.of(page, size, Sort.by(direction, "titulo")), PlanoTrabalhoModel.class);
    }

    @Override
    @Transactional
    public PlanoTrabalhoModel findById(Integer id) {
        return planoTrabalhoRepository.findById(id).orElseThrow();
    }

    @Override
    public PlanoTrabalhoModel save(PlanoTrabalhoModel planoTrabalho) {
        return planoTrabalhoRepository.save(planoTrabalho);
    }

    @Override
    public PlanoTrabalhoModel update(PlanoTrabalhoModel planoTrabalho) throws NotFoundException {
        if (!planoTrabalhoRepository.existsById(planoTrabalho.getId())) {
            throw new NotFoundException();
        }
        return this.save(planoTrabalho);
    }


}

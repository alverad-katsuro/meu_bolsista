package alveradkatsuro.com.br.meu_bolsista.repository.usuario_processo_seletivo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import alveradkatsuro.com.br.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModelId;

public interface UsuarioProcessoSeletivoRepository
        extends CrudRepository<UsuarioProcessoSeletivoModel, UsuarioProcessoSeletivoModelId> {

    List<UsuarioProcessoSeletivoModel> findAll();

    Page<UsuarioProcessoSeletivoModel> findAll(Pageable pageable);

    <T> Page<T> findBy(Pageable pageable, Class<T> tipo);

}

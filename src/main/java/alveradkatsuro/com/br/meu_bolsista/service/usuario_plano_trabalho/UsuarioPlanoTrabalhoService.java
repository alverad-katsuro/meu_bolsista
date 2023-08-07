package alveradkatsuro.com.br.meu_bolsista.service.usuario_plano_trabalho;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModelId;
import alveradkatsuro.com.br.meu_bolsista.projection.usuario_plano_trabalho.novo_plano.UsuarioNovoPlanoProjection;

public interface UsuarioPlanoTrabalhoService {

        public UsuarioPlanoTrabalhoModel findById(String usuarioId, Integer usuarioPlanoTrabalhoId);

        public UsuarioPlanoTrabalhoModel findById(UsuarioPlanoTrabalhoModelId id);

        public List<UsuarioNovoPlanoProjection> findAllUsuariosInPlanoTrabalho(Integer usuarioPlanoTrabalhoId,
                        String role);

        public Page<UsuarioPlanoTrabalhoModel> findAll(Integer page, Integer size, Direction direction,
                        String[] properties);

        public <T> Page<T> findBy(Integer page, Integer size, Direction direction, String[] properties, Class<T> tipo);

        public UsuarioPlanoTrabalhoModel save(UsuarioPlanoTrabalhoModel usuarioPlanoTrabalho);

        public UsuarioPlanoTrabalhoModel update(UsuarioPlanoTrabalhoModel usuarioPlanoTrabalho)
                        throws NotFoundException;

        public void deleteById(UsuarioPlanoTrabalhoModelId id);

        public void deleteById(String usuarioId, Integer usuarioPlanoTrabalhoId);

}

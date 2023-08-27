package alveradkatsuro.com.br.meu_bolsista.service.usuario_plano_trabalho;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import alveradkatsuro.com.br.meu_bolsista.dto.usuario_plano_trabalho.UsuarioNovoPlanoDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.usuario_plano_trabalho.UsuarioPlanoDTO;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModelId;

public interface UsuarioPlanoTrabalhoService {

        public UsuarioPlanoTrabalhoModel findById(String usuarioId, Integer usuarioPlanoTrabalhoId);

        public UsuarioPlanoTrabalhoModel findById(UsuarioPlanoTrabalhoModelId id);

        public List<UsuarioNovoPlanoDTO> findAllUsuariosInPlanoTrabalho(Integer usuarioPlanoTrabalhoId,
                        String role);

        public List<UsuarioPlanoDTO> findUsuarioPlanoInPlanoTrabalho(Integer planoTrabalhoId) throws NotFoundException;

        public Page<UsuarioPlanoTrabalhoModel> findAll(Integer page, Integer size, Direction direction,
                        String[] properties);

        public <T> Page<T> findBy(Integer page, Integer size, Direction direction, String[] properties, Class<T> tipo);

        public UsuarioPlanoTrabalhoModel save(UsuarioPlanoTrabalhoModel usuarioPlanoTrabalho);

        public UsuarioPlanoTrabalhoModel update(UsuarioPlanoTrabalhoModel usuarioPlanoTrabalho)
                        throws NotFoundException;

        public void deleteById(UsuarioPlanoTrabalhoModelId id);

        public void deleteById(String usuarioId, Integer usuarioPlanoTrabalhoId);

}

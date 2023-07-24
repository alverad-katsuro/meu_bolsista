package alveradkatsuro.com.br.meu_bolsista.service.usuario;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import alveradkatsuro.com.br.meu_bolsista.enumeration.Authority;
import alveradkatsuro.com.br.meu_bolsista.exceptions.InvalidRequestException;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import alveradkatsuro.com.br.meu_bolsista.projection.usuario_plano_trabalho.novo_plano.UsuarioNovoPlanoProjection;

/**
 * Interface responsável por especificar os metodos a serem implementados.
 *
 * @author Alfredo Gabriel, Camilo Santos
 * @since 26/03/2023
 * @version 1.0.1
 */
public interface UsuarioService extends UserDetailsService {

	/**
	 * Método responsável por persistir determinado usuário no banco de dados.
	 *
	 * @param usuarioModel Dados do usuário
	 * @return Dados após serem gravados no banco de dados.
	 */
	public UsuarioModel save(UsuarioModel usuarioModel);

	/**
	 * Método responsável por encontrar um determinado usuário por sua ID.
	 *
	 * @param idUsuario ID de um usuário
	 * @return
	 */
	public UsuarioModel findById(Integer idUsuario);

	/**
	 * Método responsável por encontrar todos os usuários cadastrados.
	 *
	 * @return Lista de objetos da classe UsuarioModel.
	 */
	public List<UsuarioModel> findAll();

	/**
	 * Método responsável por atualizar dados de um usuário cadastrado.
	 *
	 * @param usuario objeto usuário
	 * @return
	 */
	public UsuarioModel update(UsuarioModel usuario) throws InvalidRequestException;

	/**
	 * Método responsável por deletar um usuário cadastrado por sua ID.
	 *
	 * @param idUsuario ID de um usuário
	 */
	public boolean deleteById(Integer idUsuario);

	/**
	 * Método responsável por encontrar um determinado usuário por seu username.
	 *
	 */
	public UsuarioModel loadUserByUsername(String username);

	/**
	 * Método responsável por verificar se existe um determinado elemento.
	 *
	 * @param id
	 * @param createdBy
	 * @return
	 */
	public boolean existsByIdAndCreatedBy(Integer id, Integer createdBy);

	public boolean existsByUsername(String username);

	List<UsuarioNovoPlanoProjection> findUsuariosNotInPlanoTrabalho(Integer planoTrabalhoId, Authority authority);

}

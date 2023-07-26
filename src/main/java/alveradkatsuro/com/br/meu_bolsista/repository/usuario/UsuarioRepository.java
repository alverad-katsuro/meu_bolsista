package alveradkatsuro.com.br.meu_bolsista.repository.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import alveradkatsuro.com.br.meu_bolsista.enumeration.Authority;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;

/**
 * Interface utilizada para realizar a comunicação entre a aplicação é o banco
 * de dados.
 * Nota: O Spring Boot irá cuidar da implementação da mesma.
 *
 * @author Alfredo Gabriel
 * @since 26/03/2023
 * @version 1.0
 */
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Integer> {

	List<UsuarioModel> findAll();

	boolean existsByIdAndCreatedBy(Integer id, Integer createdBy);

	boolean existsByEmailIgnoreCase(String username);

	Optional<UsuarioModel> findByEmailIgnoreCase(String username);

	@Query(value = "select  " +
			"    u.id as id, " +
			"    u.nome as nome, " +
			"    CASE WHEN upt.usuario IS NOT NULL and :planoTrabalhoId != 0 THEN true ELSE false END AS participante " +
			"FROM " +
			"    usuario u " +
			"LEFT JOIN " +
			"    usuario_plano_trabalho upt ON u.id = upt.usuario.id and upt.planoTrabalho.id = :planoTrabalhoId " +
			"and :authority member of u.authorities")
	<T> List<T> findUsuariosNotInPlanoTrabalho(Integer planoTrabalhoId, Authority authority, Class<T> tipo);
}

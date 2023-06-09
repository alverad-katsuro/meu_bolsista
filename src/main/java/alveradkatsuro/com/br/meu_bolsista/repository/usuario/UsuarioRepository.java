package alveradkatsuro.com.br.meu_bolsista.repository.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

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

	boolean existsByIdAndCreatedById(Integer id, Integer createdBy);

	boolean existsByEmailIgnoreCase(String username);

	Optional<UsuarioModel> findByEmailIgnoreCase(String username);
}

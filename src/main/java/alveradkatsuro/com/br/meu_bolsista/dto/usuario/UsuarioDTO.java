package alveradkatsuro.com.br.meu_bolsista.dto.usuario;

import java.util.HashSet;
import java.util.Set;

import alveradkatsuro.com.br.meu_bolsista.enumeration.Grupos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Nota: Use este DTO somente para persistir o usuário, não retorne o mesmo.
 *
 * @author Alfredo Gabriel, Camilo Santos
 * @since 26/03/2023
 * @version 1.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

	private Integer id;

	@NotBlank(message = "Infome um usuário.")
	private String username;

	@NotBlank(message = "Infome uma senha.")
	private String password;

	@Email(message = "Informe um e-mail valido.")
	@NotBlank(message = "Infome um email.")
	private String email;

	@NotBlank(message = "Infome um nome.")
	private String nome;

	@Builder.Default
	private Set<Grupos> grupos = new HashSet<>();

}

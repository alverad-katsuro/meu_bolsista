package alveradkatsuro.com.br.meu_bolsista.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Encapsulamento da solicitação de autenticação.
 *
 * @author Alfredo Gabriel
 * @since 13/05/2023
 * @version 1.1
 */
@Data
public class AuthRequest {

	@NotBlank(message = "Informe um usuário.")
	private String username;

	@NotBlank(message = "Informe uma senha.")
	private String password;
}

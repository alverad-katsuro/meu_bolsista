package alveradkatsuro.com.br.meu_bolsista.dto.usuario;

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

	private String id;

	private String email;

	private String nome;

	private String lattes;

	private String picture;

}

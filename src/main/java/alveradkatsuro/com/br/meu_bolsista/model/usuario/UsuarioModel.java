package alveradkatsuro.com.br.meu_bolsista.model.usuario;

import java.util.Set;

import alveradkatsuro.com.br.meu_bolsista.model.audit.Auditable;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModel;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Representação da tabela Usuario presente no banco de dados.
 * Esta tabela tem como finalidade representar os usuários que podem realizar
 * login no sistema.
 *
 * @author Alfredo Gabriel
 * @since 26/03/2023
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "usuario")
@EqualsAndHashCode(callSuper = false)
public class UsuarioModel extends Auditable {

	@Id
	@Column(name = "id_usuario", unique = true, nullable = false)
	private String id;

	@Column(name = "username_usuario", nullable = false, unique = true, length = 50)
	private String username;

	@Column(name = "email_usuario", nullable = false, unique = true, length = 50)
	private String email;

	@Column(name = "nome_usuario", nullable = false, unique = false, length = 100)
	private String nome;

	@Column(name = "lattes_usuario", nullable = true, unique = false, length = 255)
	private String lattes;

	@Column(name = "imagem_usuario", nullable = true, unique = false, length = 255)
	private String imagemUrl;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private Set<UsuarioPlanoTrabalhoModel> planosTrabalhos;

	@Column(name = "grupos")
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "id_usuario"), uniqueConstraints = @UniqueConstraint(columnNames = {
			"id_usuario", "grupos" }))
	private Set<String> roles;

	@Transient
	public String getFirstName() {
		return this.nome.split(" ")[0];
	}

	@Transient
	public String getLastName() {
		return this.nome.replace(getFirstName(), "");
	}

}

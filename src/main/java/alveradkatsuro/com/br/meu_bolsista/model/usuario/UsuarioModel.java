package alveradkatsuro.com.br.meu_bolsista.model.usuario;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import alveradkatsuro.com.br.meu_bolsista.enumeration.Grupos;
import alveradkatsuro.com.br.meu_bolsista.model.audit.Auditable;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@EqualsAndHashCode(callSuper = false, exclude = "grupos")
public class UsuarioModel extends Auditable implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", unique = true, nullable = false)
	private Integer id;

	@Column(name = "login_usuario", unique = true, nullable = false, length = 50)
	private String username;

	@Column(name = "senha_usuario", nullable = false, unique = false, length = 80)
	private String password;

	@Column(name = "email", nullable = false, unique = true, length = 50)
	private String email;

	@Column(name = "nome_usuario", nullable = false, unique = false, length = 100)
	private String nome;

	@Column(name = "lattes_usuario", nullable = false, unique = false, length = 255)
	private String lattes;

	@Column(name = "grupos")
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Grupos.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "usuario_grupos", joinColumns = @JoinColumn(name = "id_usuario"), uniqueConstraints = @UniqueConstraint(columnNames = {
			"id_usuario", "grupos" }))
	private Set<Grupos> grupos;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grupos
				.stream()
				.map(grupo -> new SimpleGrantedAuthority(grupo.name())).toList();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.ativo;
	}

	@Transient
	public String getFirstName() {
		return this.nome.split(" ")[0];
	}

	@Transient
	public String getLastName() {
		return this.nome.replace(getFirstName(), "");
	}
}

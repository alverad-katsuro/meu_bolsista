package alveradkatsuro.com.br.meu_bolsista.repository.usuario_plano_trabalho;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import alveradkatsuro.com.br.meu_bolsista.enumeration.Authority;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModelId;

public interface UsuarioPlanoTrabalhoRepository
		extends CrudRepository<UsuarioPlanoTrabalhoModel, UsuarioPlanoTrabalhoModelId> {

	@Query(value = "select " +
			"  u.id as id, " +
			"  u.nome as nome, " +
			"  case when upt.cargaHoraria is null then 0 else upt.cargaHoraria end as cargaHoraria, " +
			"  case when upt.usuario is null then false else true end as participante, " +
			"  upt.planoTrabalho.id as planoTrabalhoId " +
			"from " +
			"  usuario u " +
			"left join usuario_plano_trabalho upt on " +
			"  upt.usuario.id = u.id " +
			"  and upt.planoTrabalho.id = :planoTrabalhoId " +
			"and :authority member of u.authorities")
	<T> List<T> findAllUsuariosInPlanoTrabalho(Integer planoTrabalhoId, Authority authority, Class<T> tipo);

	Page<UsuarioPlanoTrabalhoModel> findAll(Pageable pageable);

	<T> Page<T> findBy(Pageable pageable, Class<T> tipo);

}

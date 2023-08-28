package alveradkatsuro.com.br.meu_bolsista.repository.usuario_plano_trabalho;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModelId;
import alveradkatsuro.com.br.meu_bolsista.projection.usuario_plano_trabalho.novo_plano.UsuarioPlanoProjection;

public interface UsuarioPlanoTrabalhoRepository
		extends CrudRepository<UsuarioPlanoTrabalhoModel, UsuarioPlanoTrabalhoModelId> {

	@Query(value = "select " +
			"  upt.id.usuarioId as id, " +
			"  '' as nome, " +
			"  case " +
			"    when upt.cargaHoraria is null then 0 " +
			"    else upt.cargaHoraria " +
			"  end as cargaHoraria, " +
			"  case " +
			"    when upt.id.usuarioId is null then false " +
			"    else true " +
			"  end as participante, " +
			"  upt.planoTrabalho.id as planoTrabalhoId " +
			"from " +
			"  usuario_plano_trabalho upt " +
			"where " +
			"  upt.planoTrabalho.id = :planoTrabalhoId " +
			"and  upt.id.usuarioId = :usuarioId ")

	<P> Optional<P> findAllUsuariosInPlanoTrabalho(Integer planoTrabalhoId, String usuarioId, Class<P> projection);

	Page<UsuarioPlanoTrabalhoModel> findAll(Pageable pageable);

	<T> Page<T> findBy(Pageable pageable, Class<T> tipo);

	List<UsuarioPlanoProjection> findByPlanoTrabalhoId(Integer planoTrabalhoId);

	boolean existsByIdUsuarioIdAndIdPlanoTrabalhoId(String usuarioId, Integer planoTrabalhoId);

}

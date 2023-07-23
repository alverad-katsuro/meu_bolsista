package alveradkatsuro.com.br.meu_bolsista.repository.usuario_plano_trabalho;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import alveradkatsuro.com.br.meu_bolsista.model.usuario_plano_trabalho.UsuarioPlanoTrabalhoModelId;
import jakarta.persistence.Tuple;

public interface UsuarioPlanoTrabalhoRepository
        extends CrudRepository<UsuarioPlanoTrabalhoRepository, UsuarioPlanoTrabalhoModelId> {

    @Query(value = "select " +
            "   u.id_usuario, " +
            "   u.nome_usuario, " +
            "CASE WHEN upt.usuario_id_usuario IS NOT NULL THEN true ELSE false END AS participa " +
            "FROM " +
            "   usuario u " +
            "LEFT JOIN " +
            "   usuario_plano_trabalho upt ON u.id_usuario = upt.usuario_id_usuario " +
            "where upt.planotrabalho_id_plano_trabalho = $planoTrabalhoId ")
    List<Tuple> find(Integer planoTrabalhoId);

}

package alveradkatsuro.com.br.meu_bolsista.service.usuario_plano_trabalho;

import java.util.List;

import org.springframework.stereotype.Service;

import alveradkatsuro.com.br.meu_bolsista.enumeration.Authority;
import alveradkatsuro.com.br.meu_bolsista.projection.usuario_plano_trabalho.novo_plano.UsuarioNovoPlanoProjection;
import alveradkatsuro.com.br.meu_bolsista.repository.usuario_plano_trabalho.UsuarioPlanoTrabalhoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioPlanoTrabalhoService {

    private final UsuarioPlanoTrabalhoRepository usuarioPlanoTrabalhoRepository;

    public List<UsuarioNovoPlanoProjection> findAllUsuariosInPlanoTrabalho(Integer planoTrabalhoId,
            Authority authority) {
        return usuarioPlanoTrabalhoRepository.findAllUsuariosInPlanoTrabalho(planoTrabalhoId, authority,
                UsuarioNovoPlanoProjection.class);
    }
}
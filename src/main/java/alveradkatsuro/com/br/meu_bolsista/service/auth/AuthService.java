package alveradkatsuro.com.br.meu_bolsista.service.auth;

import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService tokenService;

    public String authenticate(UsuarioModel usuarioModel) {
        String scope = usuarioModel.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        return tokenService.generateToken(usuarioModel, scope);
    }
}

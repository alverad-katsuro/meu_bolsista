package alveradkatsuro.com.br.meu_bolsista.service.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import alveradkatsuro.com.br.meu_bolsista.config.properties.TokenProperties;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import alveradkatsuro.com.br.meu_bolsista.util.JwtUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final TokenProperties tokenProperties;

    public String generateToken(Authentication authentication) {
        UsuarioModel userPrincipal = (UsuarioModel) authentication.getPrincipal();
        Instant now = Instant.now();

        var jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("API Meu Bolsista - UFPA")
                .subject(userPrincipal.getUsername())
                .issuedAt(now)
                .expiresAt(now.plus(tokenProperties.getExpiresHours(), ChronoUnit.HOURS))
                .claim(JwtUtils.NOME.getPropriedade(), userPrincipal.getName())
                .claim(JwtUtils.USER_ID.getPropriedade(), userPrincipal.getId())
                .claim(JwtUtils.IMAGEM_URL.getPropriedade(), userPrincipal.getImagemUrl())
                .claim(JwtUtils.SCOPE.getPropriedade(), authentication.getAuthorities());
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet.build())).getTokenValue();
    }

    public String generateToken(UsuarioModel userModel, String scope) {
        Instant now = Instant.now();
        var jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("API Meu Bolsista - UFPA")
                .subject(userModel.getUsername())
                .issuedAt(now)
                .expiresAt(now.plus(tokenProperties.getExpiresHours(), ChronoUnit.HOURS))
                .claim(JwtUtils.NOME.getPropriedade(), userModel.getFirstName())
                .claim(JwtUtils.USER_ID.getPropriedade(), userModel.getId())
                .claim(JwtUtils.IMAGEM_URL.getPropriedade(), userModel.getImagemUrl())
                .claim(JwtUtils.SCOPE.getPropriedade(), scope);
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet.build())).getTokenValue();

    }

    public Integer getUserIdFromToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return Integer.parseInt(jwt.getClaim(JwtUtils.USER_ID.getPropriedade()).toString());
    }

    public Integer getIdUsuario(JwtAuthenticationToken jwtAuthenticationToken) {
        return Integer.parseInt(
                jwtAuthenticationToken.getTokenAttributes().get(JwtUtils.USER_ID.getPropriedade()).toString());
    }

    private boolean isTokenExpired(String token) {
        Instant exp = jwtDecoder.decode(token).getExpiresAt();
        if (exp != null) {
            return exp.isBefore(Instant.now());
        } else {
            return true;
        }
    }

    public boolean validateToken(String authToken) {
        return (!isTokenExpired(authToken));
    }

}

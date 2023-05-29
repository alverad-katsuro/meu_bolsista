package alveradkatsuro.com.br.meu_bolsista.service.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import alveradkatsuro.com.br.meu_bolsista.config.properties.TokenProperties;
import alveradkatsuro.com.br.meu_bolsista.enumeration.JwtUtils;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final TokenProperties tokenProperties;

    public String extractUsername(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

    public String generateToken(UsuarioModel userModel, String scope) {
        Instant now = Instant.now();
        var jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("API Meu Egresso - UFPA")
                .subject(userModel.getUsername())
                .issuedAt(now)
                .expiresAt(now.plus(tokenProperties.getExpiresHours(), ChronoUnit.HOURS))
                .claim(JwtUtils.NOME.getPropriedade(), userModel.getFirstName())
                .claim(JwtUtils.SOBRENOME.getPropriedade(), userModel.getLastName())
                .claim(JwtUtils.USER_ID.getPropriedade(), userModel.getId())
                .claim(JwtUtils.SCOPE.getPropriedade(), scope);
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet.build())).getTokenValue();

    }

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        var jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiresAt(now.plus(tokenProperties.getExpiresHours(), ChronoUnit.HOURS));
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet.build())).getTokenValue();

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
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

}

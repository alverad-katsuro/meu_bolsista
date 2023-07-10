package alveradkatsuro.com.br.meu_bolsista.config.oauth2.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.StringUtils;

public class CustomTokenResponseConverter implements
    Converter<Map<String, String>, OAuth2AccessTokenResponse> {

  @Override
  public OAuth2AccessTokenResponse convert(Map<String, String> tokenResponseParameters) {
    String accessToken = tokenResponseParameters.get(OAuth2ParameterNames.ACCESS_TOKEN);
    String refreshToken = tokenResponseParameters.get(OAuth2ParameterNames.REFRESH_TOKEN);
    long expiresIn = Long.parseLong(tokenResponseParameters.get(OAuth2ParameterNames.EXPIRES_IN));

    Set<String> scopes = Collections.emptySet();
    if (tokenResponseParameters.containsKey(OAuth2ParameterNames.SCOPE)) {
      String scope = tokenResponseParameters.get(OAuth2ParameterNames.SCOPE);
      scopes = Arrays.stream(StringUtils.delimitedListToStringArray(scope, " "))
          .collect(Collectors.toSet());
    }

    Map<String, Object> additionalParameters = new HashMap<>();
    additionalParameters.put(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken);

    return OAuth2AccessTokenResponse.withToken(accessToken)
        .tokenType(OAuth2AccessToken.TokenType.BEARER)
        .expiresIn(expiresIn)
        .scopes(scopes)
        .refreshToken(refreshToken)
        .additionalParameters(Collections.unmodifiableMap(additionalParameters))
        .build();
  }
}

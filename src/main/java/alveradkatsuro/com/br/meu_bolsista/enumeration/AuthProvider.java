package alveradkatsuro.com.br.meu_bolsista.enumeration;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum AuthProvider {
    LOCAL("local"),
    FACEBOOK("facebook"),
    GOOGLE("google"),
    GITHUB("github");

    private String providerName;

    AuthProvider(String providerName) {
        this.providerName = providerName;
    }

    public static AuthProvider findProviderName(String providerName) {
        return Arrays.stream(AuthProvider.values()).filter(e -> e.getProviderName().equalsIgnoreCase(providerName))
                .findAny().orElse(null);
    }
}

package br.com.alverad.meu_bolsista.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "services.anexos-store-api")
public record AnexosStoreApiProperties(String baseUrl, String salvarArquivo, String consultarArquivo) {

    public String getUrlConsultarArquivo(String id) {
        return this.consultarArquivo + id;
    }

}

package alveradkatsuro.com.br.meu_bolsista.service.arquivo_storage.impl;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import alveradkatsuro.com.br.meu_bolsista.config.properties.AnexosStoreApiProperties;
import alveradkatsuro.com.br.meu_bolsista.service.arquivo_storage.ArquivoStorageService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ArquivoStorageServiceImpl implements ArquivoStorageService {

    private final WebClient webClient;

    private final AnexosStoreApiProperties anexosStoreApiProperties;

    @Override
    public void delete(String id) {
        webClient.delete()
                .uri("/arquivos/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public String salvarArquivo(Optional<String> id, Optional<String> nome, MultipartFile arquivo) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("arquivo", arquivo.getResource());
        nome.ifPresent(e -> builder.part("nome", e));
        id.ifPresent(e -> builder.part("id", e));
        return webClient.post()
                .uri(anexosStoreApiProperties.salvarArquivo())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA.toString())
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class).block();
    }

    @Override
    public String salvarArquivo(Optional<String> nome, MultipartFile arquivo) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("arquivo", arquivo.getResource());
        nome.ifPresent(e -> builder.part("nome", e));
        Mono<String> idReturn = webClient.post()
                .uri(anexosStoreApiProperties.salvarArquivo())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA.toString())
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class);
        return idReturn.block();
    }

}

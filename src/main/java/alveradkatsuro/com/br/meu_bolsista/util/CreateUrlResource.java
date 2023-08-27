package alveradkatsuro.com.br.meu_bolsista.util;

import java.io.IOException;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import alveradkatsuro.com.br.meu_bolsista.controller.arquivo.ArquivoController;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CreateUrlResource {

    private CreateUrlResource(){}

    public static String createUrlResource(String resourceId) {
        try {
            return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                    ArquivoController.class).recuperarArquivo(resourceId))
                    .toUri().toString();
        } catch (IllegalArgumentException | SecurityException | IOException | NotFoundException e) {
            log.error("Erro ao criar url do recurso: {}", resourceId);
            return null;
        }
    }
}

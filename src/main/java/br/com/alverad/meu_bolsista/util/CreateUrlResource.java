package br.com.alverad.meu_bolsista.util;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import br.com.alverad.meu_bolsista.controller.arquivo.ArquivoController;
import br.com.alverad.meu_bolsista.exceptions.NotFoundException;
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

    public static String createUrlResource(ObjectId resourceId) {
        return createUrlResource(resourceId.toString());
    }
}

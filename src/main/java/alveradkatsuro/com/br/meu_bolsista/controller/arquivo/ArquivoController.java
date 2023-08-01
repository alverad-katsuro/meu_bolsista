package alveradkatsuro.com.br.meu_bolsista.controller.arquivo;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.service.arquivo.ArquivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/arquivo")
public class ArquivoController {

    private final ArquivoService arquivoService;

    @GetMapping(value = "/{id}")
@Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<Resource> recuperarArquivo(@PathVariable String id)
            throws IllegalArgumentException, SecurityException, IOException, NotFoundException {

        GridFsResource resource = arquivoService.recuperarArquivo(new ObjectId(id));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment",
                String.format("%s.%s", resource.getFilename(), resource.getContentType().split("/")[1]));
        return ResponseEntity.ok().contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .headers(headers)
                .body(resource);
    }

}

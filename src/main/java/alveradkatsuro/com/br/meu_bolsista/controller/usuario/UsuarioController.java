package alveradkatsuro.com.br.meu_bolsista.controller.usuario;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import alveradkatsuro.com.br.meu_bolsista.annotation.CurrentUserToken;
import alveradkatsuro.com.br.meu_bolsista.dto.keycloak.user.UserDataKeycloak;
import alveradkatsuro.com.br.meu_bolsista.enumeration.ResponseType;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.exceptions.UnauthorizedRequestException;
import alveradkatsuro.com.br.meu_bolsista.service.arquivo.ArquivoService;
import alveradkatsuro.com.br.meu_bolsista.service.keycloak.KeycloakService;
import alveradkatsuro.com.br.meu_bolsista.util.CreateUrlResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/usuario")
public class UsuarioController {

    private final ArquivoService arquivoService;

    private final KeycloakService keycloakService;

    @GetMapping
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public UserDataKeycloak getCurrentUser(@CurrentUserToken String id) throws NotFoundException {
        return keycloakService.getUser(id);
    }

    @PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(security = { @SecurityRequirement(name = "Bearer") })
    public ResponseEntity<ResponseType> update(
            @Valid @RequestPart("usuario") UserDataKeycloak usuario,
            @RequestPart(required = false) MultipartFile foto,
            @CurrentUserToken String id)
            throws NotFoundException, IOException, UnauthorizedRequestException {

        if (!usuario.getId().equals(id)) {
            throw new UnauthorizedRequestException();
        }

        if (foto != null) {
            ObjectId pictureId;
            final String pictureIdString = "pictureId";
            if (usuario.getAttributes().containsKey(pictureIdString)) {
                pictureId = arquivoService
                        .salvarArquivo(new ObjectId((String) usuario.getAttributes().get(pictureIdString).get(0)), foto);
            } else {
                pictureId = arquivoService.salvarArquivo(foto);
                usuario.getAttributes().put(pictureIdString, List.of(pictureId.toString()));
            }
            final String picture = CreateUrlResource.createUrlResource(pictureId);
            usuario.getAttributes().remove("picture");
            usuario.getAttributes().put("picture", List.of(picture));
        }

        keycloakService.updateUser(usuario);

        return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                this.getClass()).getCurrentUser(usuario.getId()))
                .toUri()).body(ResponseType.SUCESS_UPDATE);
    }

}

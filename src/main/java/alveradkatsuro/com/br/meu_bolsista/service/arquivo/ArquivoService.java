package alveradkatsuro.com.br.meu_bolsista.service.arquivo;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;

import alveradkatsuro.com.br.meu_bolsista.dto.arquivo.ArquivoDTO;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArquivoService {

    private final GridFsTemplate gridFsTemplate;

    private final GridFsOperations gridFsOperations;

    public ObjectId salvarArquivo(ArquivoDTO arquivo)
            throws IOException {
        if (arquivo.getId() != null) {
            gridFsTemplate.delete(new Query(Criteria.where("_id").is(arquivo.getId())));
        }
        return gridFsTemplate.store(
                arquivo.getArquivo().getInputStream(), arquivo.getNome().orElse(UUID.randomUUID().toString()),
                arquivo.getArquivo().getContentType());
    }

    public ObjectId salvarArquivo(ObjectId id, Optional<String> nome, MultipartFile arquivo)
            throws IOException {
        if (id != null) {
            gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
        }
        return gridFsTemplate.store(
                arquivo.getInputStream(), nome.orElse(UUID.randomUUID().toString()),
                arquivo.getContentType());
    }

    public ObjectId salvarArquivo(ObjectId id, MultipartFile arquivo)
            throws IOException {
        if (id != null) {
            gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
        }
        return gridFsTemplate.store(
                arquivo.getInputStream(), UUID.randomUUID().toString(),
                arquivo.getContentType());
    }

    public ObjectId salvarArquivo(Optional<String> nome, MultipartFile arquivo)
            throws IOException {
        return gridFsTemplate.store(
                arquivo.getInputStream(), nome.orElse(UUID.randomUUID().toString()),
                arquivo.getContentType());
    }

    public ObjectId salvarArquivo(MultipartFile arquivo)
            throws IOException {
        return gridFsTemplate.store(
                arquivo.getInputStream(), UUID.randomUUID().toString(),
                arquivo.getContentType());
    }

    public GridFsResource recuperarArquivo(ObjectId id)
            throws IllegalArgumentException, SecurityException, NotFoundException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if (file == null) {
            throw new NotFoundException();
        }
        return gridFsOperations.getResource(file);

    }

}

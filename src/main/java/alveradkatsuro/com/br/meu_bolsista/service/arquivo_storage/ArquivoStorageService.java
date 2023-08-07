package alveradkatsuro.com.br.meu_bolsista.service.arquivo_storage;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface ArquivoStorageService {

    public void delete(String id);

    public String salvarArquivo(Optional<String> id, Optional<String> nome, MultipartFile arquivo);

    public String salvarArquivo(Optional<String> nome, MultipartFile arquivo);

}

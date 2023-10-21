package br.com.alverad.meu_bolsista.dto.usuario_processo_seletivo;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioProcessoSeletivoCreateDTO {

    private Integer processoSeletivoId;

    private MultipartFile arquivo;

}

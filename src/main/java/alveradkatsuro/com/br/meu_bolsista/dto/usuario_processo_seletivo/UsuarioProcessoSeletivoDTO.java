package alveradkatsuro.com.br.meu_bolsista.dto.usuario_processo_seletivo;

import java.time.LocalDateTime;

import alveradkatsuro.com.br.meu_bolsista.dto.usuario.UsuarioBasicDTO;
import alveradkatsuro.com.br.meu_bolsista.model.usuario_processo_seletivo.UsuarioProcessoSeletivoModelId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioProcessoSeletivoDTO {

    private UsuarioProcessoSeletivoModelId id;

    private Boolean aprovado = false;

    private LocalDateTime inscricao;

    private String curriculo;

    private UsuarioBasicDTO usuario;

}

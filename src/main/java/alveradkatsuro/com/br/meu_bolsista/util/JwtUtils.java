package alveradkatsuro.com.br.meu_bolsista.util;

import lombok.Getter;

@Getter
public enum JwtUtils {
    USER_ID("idUsuario"),
    NOME("nome"),
    IMAGEM_URL("imagemUrl"),
    SCOPE("scope");

    private String propriedade;

    private JwtUtils(String propriedade) {
        this.propriedade = propriedade;
    }
}

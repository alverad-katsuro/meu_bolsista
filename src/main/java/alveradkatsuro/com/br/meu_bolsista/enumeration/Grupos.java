package alveradkatsuro.com.br.meu_bolsista.enumeration;

import lombok.Getter;

@Getter
public enum Grupos {

    ADMIN("ROLE_ADMIN"),
    PESQUISADOR("ROLE_PESQUISADOR"),
    CANDIDATO("ROLE_CANDIDATO");

    private String code;
    Grupos(String code) {
        this.code = code;
    }
}

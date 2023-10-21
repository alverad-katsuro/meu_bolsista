package br.com.alverad.meu_bolsista.enumeration;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
public enum Authority implements GrantedAuthority {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_PESQUISADOR("ROLE_PESQUISADOR"),
    ROLE_CANDIDATO("ROLE_CANDIDATO"),
    ROLE_USER("ROLE_USER");

    private String code;

    Authority(String code) {
        this.code = code;
    }

    @Override
    public String getAuthority() {
        return this.code;
    }
}

package br.com.alverad.meu_bolsista.enumeration;

import lombok.Getter;

@Getter
public enum ColunaKanban {
    TODO("TODO"),
    IN_PROGRESS("In Progress"),
    DONE("DONE");

    private String value;

    private ColunaKanban(String value) {
        this.value = value;
    }
}

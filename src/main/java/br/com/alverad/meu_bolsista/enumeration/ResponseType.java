package br.com.alverad.meu_bolsista.enumeration;

import lombok.Getter;

@Getter
public enum ResponseType {

    SUCESS_SAVE("Dado salvo com sucesso"),

    SUCESS_UPDATE("Dado atualizado com sucesso"),

    SUCESS_DELETE("Dado deletado com sucesso"),

    RELATORIO_SUBMETIDO("Relatorio submetido."),

    PLANO_FINALIZADO("Plano de trabalho finalizado com sucesso."),

    PLANO_REABERTO("Plano de trabalho reaberto."),

    FAIL_DELETE("Dado não deletado"),

    SUCESS_IMAGE_SAVE("Imagem salva com sucesso"),

    SUCESS_IMAGE_DELETE("Imagem deletada com sucesso"),

    FAIL_IMAGE_DELETE("Usuário não tem imagem para deletar");

    private String message;

    private ResponseType(String message) {
        this.message = message;
    }

}

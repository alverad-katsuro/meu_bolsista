package alveradkatsuro.com.br.meu_bolsista.enumeration;

import lombok.Getter;

@Getter
public enum ErrorType {

    REPORT_001("Requisição inválida.", "RP-001"),

    REPORT_002("Senha inválida.", "RP-003"),

    REPORT_003("Recurso não encontrado.", "RP-004"),

    REPORT_004("Erro no JSON do corpo da requisição.", "RP-005"),

    REPORT_005("Erro ao enviar o email.", "RP-006"),
    REPORT_006("Erro ao enviar o email.", "RP-006"),

    UNAUTHORIZED_TRANSACTION("Permisões insuficientes para realizar esta transação", "SECURITY-001"),

    DATABASE_001("Falha ao tentar salvar no banco de dados.", "DB-001"),

    DATABASE_002("Nenhuma mensagem de email do tipo %s cadastrada.", "DB-002"),

    USER_001("Usuário %s não existe.", "US-001"),
    USER_002("Usuario ou Senha inválida.", "US-002");

    private String message;
    private String internalCode;

    private ErrorType(String message, String internalCode) {
        this.message = message;
        this.internalCode = internalCode;
    }
}

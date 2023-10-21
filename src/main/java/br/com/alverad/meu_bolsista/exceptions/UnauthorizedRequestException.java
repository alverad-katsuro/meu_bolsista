package br.com.alverad.meu_bolsista.exceptions;

import br.com.alverad.meu_bolsista.enumeration.ErrorType;
import lombok.Getter;

@Getter
public class UnauthorizedRequestException extends Exception {

    private final String internalCode;

    public UnauthorizedRequestException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }

    public UnauthorizedRequestException(String message) {
        super(message);
        this.internalCode = ErrorType.UNAUTHORIZED_TRANSACTION.getInternalCode();
    }

    public UnauthorizedRequestException() {
        super(ErrorType.UNAUTHORIZED_TRANSACTION.getMessage());
        this.internalCode = ErrorType.UNAUTHORIZED_TRANSACTION.getInternalCode();
    }
}

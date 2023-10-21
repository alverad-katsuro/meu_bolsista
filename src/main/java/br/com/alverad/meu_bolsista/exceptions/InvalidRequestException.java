package br.com.alverad.meu_bolsista.exceptions;

import br.com.alverad.meu_bolsista.enumeration.ErrorType;
import lombok.Getter;

@Getter
public class InvalidRequestException extends Exception {

    private final String internalCode;

    public InvalidRequestException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }

    public InvalidRequestException() {
        super(ErrorType.REPORT_001.getMessage());
        this.internalCode = ErrorType.REPORT_001.getInternalCode();
    }
}

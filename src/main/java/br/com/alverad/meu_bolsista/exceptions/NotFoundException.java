package br.com.alverad.meu_bolsista.exceptions;

import br.com.alverad.meu_bolsista.enumeration.ErrorType;
import lombok.Getter;

@Getter
public class NotFoundException extends Exception {

    private final String internalCode;

    public NotFoundException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }

    public NotFoundException() {
        super(ErrorType.REPORT_003.getMessage());
        this.internalCode = ErrorType.REPORT_003.getInternalCode();
    }
}

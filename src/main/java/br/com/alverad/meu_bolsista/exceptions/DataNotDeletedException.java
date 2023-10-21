package br.com.alverad.meu_bolsista.exceptions;

import br.com.alverad.meu_bolsista.enumeration.ErrorType;
import lombok.Getter;

@Getter
public class DataNotDeletedException extends Exception {

    private final String internalCode;

    public DataNotDeletedException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }

    public DataNotDeletedException() {
        super(ErrorType.DATABASE_001.getMessage());
        this.internalCode = ErrorType.DATABASE_001.getInternalCode();
    }

}

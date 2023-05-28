package alveradkatsuro.com.br.meu_bolsista.exceptions;

import alveradkatsuro.com.br.meu_bolsista.enumeration.ErrorType;
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

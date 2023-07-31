package alveradkatsuro.com.br.meu_bolsista.exceptions;

import alveradkatsuro.com.br.meu_bolsista.enumeration.ErrorType;
import lombok.Getter;

@Getter
public class DeadlineRegistrationException extends Exception {

    private final String internalCode;

    public DeadlineRegistrationException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }

    public DeadlineRegistrationException() {
        super(ErrorType.REPORT_007.getMessage());
        this.internalCode = ErrorType.REPORT_007.getInternalCode();
    }
}

package alveradkatsuro.com.br.meu_bolsista.exceptions;

import alveradkatsuro.com.br.meu_bolsista.enumeration.ErrorType;
import lombok.Getter;

@Getter
public class UnsubmittedReportException extends Exception {

    private final String internalCode;

    public UnsubmittedReportException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }

    public UnsubmittedReportException() {
        super(ErrorType.REPORT_008.getMessage());
        this.internalCode = ErrorType.REPORT_008.getInternalCode();
    }
}

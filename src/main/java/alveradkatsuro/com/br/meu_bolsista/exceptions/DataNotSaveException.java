package alveradkatsuro.com.br.meu_bolsista.exceptions;

import lombok.Getter;

@Getter
public class DataNotSaveException extends Exception {

    private final String internalCode;

    public DataNotSaveException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }

}

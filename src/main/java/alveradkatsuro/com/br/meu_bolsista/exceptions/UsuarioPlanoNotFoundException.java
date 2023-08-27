package alveradkatsuro.com.br.meu_bolsista.exceptions;

public class UsuarioPlanoNotFoundException extends RuntimeException {
    public UsuarioPlanoNotFoundException(String message) {
        super(message);
    }

    public UsuarioPlanoNotFoundException(String message, Exception exception) {
        super(message, exception);
    }
}

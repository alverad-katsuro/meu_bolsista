package alveradkatsuro.com.br.meu_bolsista.controller.adviser;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import alveradkatsuro.com.br.meu_bolsista.enumeration.ErrorType;
import alveradkatsuro.com.br.meu_bolsista.erros.ErrorResponse;
import alveradkatsuro.com.br.meu_bolsista.exceptions.DataNotDeletedException;
import alveradkatsuro.com.br.meu_bolsista.exceptions.DataNotSaveException;
import alveradkatsuro.com.br.meu_bolsista.exceptions.InvalidRequestException;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NameAlreadyExistsException;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.exceptions.UnauthorizedRequestException;
import jakarta.mail.MessagingException;

/**
 * Responsável por tratar as exceções gerais da aplicação.
 *
 * @author Alfredo Gabriel
 * @since 26/03/2023
 * @version 1.0
 */
@RestControllerAdvice
public class ControllerAdvise {

	/**
	 * Caso algum recurso não seja encontrado no banco de dados sera retornado uma
	 * mensagem padronizada.
	 *
	 * @param ex Excessão capturada.
	 * @return Menssagem.
	 * @author Alfredo Gabriel
	 * @since 26/03/2023
	 */
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorResponse handleNotfoundException(NotFoundException ex) {
		return ErrorResponse.builder()
				.message(ex.getMessage())
				.internalCode(ex.getInternalCode())
				.build();
	}

	/**
	 * Caso alguma entidade não seja valida (dados diferentes do esperado), será
	 * retornado o atributo e a menssagem pre definida.
	 *
	 * @param ex Excessão capturada.
	 * @return Mapa contendo atributo e mensagem de erro.
	 * @author Alfredo Gabriel
	 * @since 26/03/2023
	 */
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put(ErrorType.REPORT_001.getMessage(), ErrorType.REPORT_001.getInternalCode());
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	/**
	 * Caso a autenticação não seja bem sucedida, será retornado a mensagem
	 * pre-estabelicida.
	 *
	 * @param ex Excessão capturada.
	 * @return Mensagem
	 * @author Alfredo Gabriel
	 * @since 26/03/2023
	 */
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(BadCredentialsException.class)
	public ErrorResponse handleBadCredentialsException(BadCredentialsException ex) {
		return ErrorResponse.builder()
				.message(ErrorType.USER_002.getMessage())
				.technicalMessage(ex.getLocalizedMessage())
				.internalCode(ErrorType.USER_002.getInternalCode())
				.build();
	}

	/**
	 * Caso a autenticação não seja bem sucedida, será retornado a mensagem
	 * pre-estabelicida.
	 *
	 * @param ex Excessão capturada.
	 * @return Mensagem
	 * @author Alfredo Gabriel
	 * @since 26/03/2023
	 */
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AuthenticationException.class)
	public ErrorResponse handleAuthenticationException(AuthenticationException ex) {
		return ErrorResponse.builder()
				.message(ErrorType.USER_002.getMessage())
				.technicalMessage(ex.getLocalizedMessage())
				.internalCode(ErrorType.USER_002.getInternalCode())
				.build();
	}

	/**
	 * Caso seja realizado uma requisição invalida.
	 *
	 * @param ex Excessão capturada.
	 * @return Mensagem
	 * @author Alfredo Gabriel
	 * @since 26/03/2023
	 */
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedRequestException.class)
	public ErrorResponse handleUnauthorizedRequestException(UnauthorizedRequestException ex) {
		return new ErrorResponse(
				ex.getMessage(),
				ex.getLocalizedMessage(),
				ex.getInternalCode());
	}

	/**
	 * Caso seja realizado uma requisição invalida.
	 *
	 * @param ex Excessão capturada.
	 * @return Mensagem
	 * @author Alfredo Gabriel
	 * @since 26/03/2023
	 */
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(InvalidRequestException.class)
	public ErrorResponse handleInvalidRequestException(InvalidRequestException ex) {
		return new ErrorResponse(
				ex.getMessage(),
				ex.getMessage(),
				ex.getInternalCode());
	}

	/**
	 * Caso seja solicitado o delete de uma entidade não existente.
	 *
	 * @param ex Excessão capturada.
	 * @return Mensagem
	 * @author Alfredo Gabriel
	 * @since 26/03/2023
	 */
	@ExceptionHandler(DataNotDeletedException.class)
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	public ErrorResponse handleDataNotDeletedException(DataNotDeletedException ex) {
		return new ErrorResponse(
				ex.getMessage(),
				ex.getMessage(),
				ex.getInternalCode());
	}

	/**
	 * Caso a autenticação não seja encontrado nenhum elemento correspondente.
	 *
	 * @param ex Excessão capturada.
	 * @return Mensagem
	 * @author Alfredo Gabriel
	 * @since 21/04/2023
	 */
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public ErrorResponse handleNoSuchElementException(NoSuchElementException ex) {
		return new ErrorResponse(
				ErrorType.REPORT_004.getMessage(),
				ex.getLocalizedMessage(),
				ErrorType.REPORT_004.getInternalCode());
	}

	/**
	 * Caso a autenticação não seja encontrado nenum exemplo de e-mail
	 *
	 * @param ex Excessão capturada.
	 * @return Mensagem
	 * @author Alfredo Gabriel
	 * @since 21/04/2023
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ErrorResponse handleNotFound(HttpMessageNotReadableException ex) {
		return new ErrorResponse(
				ErrorType.REPORT_005.getMessage(),
				ex.getLocalizedMessage(),
				ErrorType.REPORT_005.getInternalCode());
	}

	/**
	 * Caso seja feito uma requição sem dados na lista esperada.
	 *
	 * @param ex Excessão capturada.
	 * @return Mensagem
	 * @author Alfredo Gabriel
	 * @since 21/04/2023
	 * @see {@link NameAlreadyExistsException}
	 */
	@ExceptionHandler({ NameAlreadyExistsException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorResponse handleEmptyBodyRequestListException(
			NameAlreadyExistsException ex) {
		return new ErrorResponse(
				ex.getMessage(),
				ex.getLocalizedMessage(),
				ex.getInternalCode());
	}

	/**
	 * Caso a haja problemas no envio do e-mail.
	 *
	 * @param ex Excessão capturada.
	 * @return Mensagem
	 * @author Alfredo Gabriel
	 * @since 21/04/2023
	 */
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ MailException.class, MessagingException.class })
	public ErrorResponse handleMailException(MailException ex) {
		return new ErrorResponse(
				ErrorType.REPORT_006.getMessage(),
				ex.getLocalizedMessage(),
				ErrorType.REPORT_006.getInternalCode());
	}

	/**
	 * Caso haja algum problema ao salvar o dado.
	 *
	 * @param ex Excessão capturada.
	 * @return Mensagem
	 * @author Alfredo Gabriel
	 * @since 21/04/2023
	 */
	@ExceptionHandler(DataNotSaveException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleDataNotSaveException(DataNotSaveException ex) {
		return new ErrorResponse(
				ex.getMessage(),
				ex.getLocalizedMessage(),
				ex.getInternalCode());
	}

}

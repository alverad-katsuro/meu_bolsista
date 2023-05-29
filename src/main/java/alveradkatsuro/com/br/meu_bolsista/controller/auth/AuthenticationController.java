package alveradkatsuro.com.br.meu_bolsista.controller.auth;

import java.time.Duration;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.config.properties.TokenProperties;
import alveradkatsuro.com.br.meu_bolsista.dto.auth.AuthRequest;
import alveradkatsuro.com.br.meu_bolsista.dto.auth.AuthResponse;
import alveradkatsuro.com.br.meu_bolsista.dto.usuario.UsuarioDTO;
import alveradkatsuro.com.br.meu_bolsista.enumeration.ErrorType;
import alveradkatsuro.com.br.meu_bolsista.enumeration.ResponseType;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NameAlreadyExistsException;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import alveradkatsuro.com.br.meu_bolsista.service.auth.AuthService;
import alveradkatsuro.com.br.meu_bolsista.service.auth.JwtService;
import alveradkatsuro.com.br.meu_bolsista.service.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Responsável por fornecer um end-point para a authenticação do usuario.
 *
 * @author Alfredo Gabriel
 * @since 26/03/2023
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final ModelMapper mapper;

	private final AuthService authService;

	private final UsuarioService usuarioService;

	private final TokenProperties tokenProperties;

	private final AuthenticationManager authenticationManager;

	private final JwtService jwtService;

	/**
	 * Endpoint responsavel por autentica um determinado usuário.
	 *
	 * @param authenticationRequest Recebe o usuario e senha para realizar login.
	 * @return {@link AuthResponse} Token autenticado.
	 * @author Alfredo Gabriel
	 * @throws NotFoundException
	 * @since 26/03/2023
	 */
	@PostMapping(value = "/login")
	@Operation(security = { @SecurityRequirement(name = "Bearer") })
	public ResponseEntity<AuthResponse> authenticationUser(
			@RequestBody AuthRequest authenticationRequest,
			HttpServletRequest request) {

		Authentication auth = authenticationManager.authenticate(
				UsernamePasswordAuthenticationToken.unauthenticated(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));

		String token = authService.authenticate((UsuarioModel) auth.getPrincipal());

		ResponseCookie cookie = ResponseCookie.from("Token", token)
				.httpOnly(false)
				.secure(false)
				.domain(tokenProperties.getDomain())
				.sameSite("Lax")
				.path("/")
				.maxAge(Duration.ofHours(tokenProperties.getExpiresHours()))
				.build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(AuthResponse.builder().token(token).build());
	}

	@PostMapping(value = "/atualizarCookie")
	@Operation(security = { @SecurityRequirement(name = "Bearer") })
	public ResponseEntity<AuthResponse> authenticationUser(JwtAuthenticationToken tokenAtual,
			HttpServletRequest request) {

		UsuarioModel usuario = usuarioService.findById(jwtService.getIdUsuario(tokenAtual));

		String token = authService.authenticate(usuario);

		ResponseCookie cookie = ResponseCookie.from("Token", token)
				.httpOnly(false)
				.secure(false)
				.domain(tokenProperties.getDomain())
				.sameSite("Lax")
				.path("/")
				.maxAge(Duration.ofHours(tokenProperties.getExpiresHours()))
				.build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(AuthResponse.builder().token(token).build());
	}

	/**
	 * Endpoint responsavel por cadastrar o usuário.
	 *
	 * @param usuarioDTO Estrutura de dados contendo as informações necessárias para
	 *                   persistir o Usuário.
	 * @return String confirmando a transação.
	 * @author Alfredo Gabriel
	 * @throws NotFoundException
	 * @throws NameAlreadyExistsException
	 * @see {@link UsuarioDTO}
	 * @since 26/03/2023
	 */
	@PostMapping(value = "/register")
	@ResponseStatus(code = HttpStatus.CREATED)
	@Operation(security = { @SecurityRequirement(name = "Bearer") })
	public String cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) throws NameAlreadyExistsException {
		if (usuarioService.existsByUsername(usuarioDTO.getUsername())) {
			throw new NameAlreadyExistsException(
					String.format(ErrorType.USER_001.getMessage(), usuarioDTO.getUsername()),
					ErrorType.USER_001.getInternalCode());
		}
		UsuarioModel usuarioModel = mapper.map(usuarioDTO, UsuarioModel.class);
		usuarioService.save(usuarioModel);
		return ResponseType.SUCESS_SAVE.getMessage();
	}

}

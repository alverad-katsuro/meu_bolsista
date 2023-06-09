package alveradkatsuro.com.br.meu_bolsista.controller.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import alveradkatsuro.com.br.meu_bolsista.annotation.CurrentUser;
import alveradkatsuro.com.br.meu_bolsista.exceptions.ResourceNotFoundException;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import alveradkatsuro.com.br.meu_bolsista.repository.usuario.UsuarioRepository;

@RestController
public class UserController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UsuarioModel getCurrentUser(@CurrentUser UsuarioModel userPrincipal) {
        return usuarioRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}

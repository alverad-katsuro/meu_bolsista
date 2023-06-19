package alveradkatsuro.com.br.meu_bolsista.controller.plano_trabalho;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import alveradkatsuro.com.br.meu_bolsista.annotation.CurrentUser;
import alveradkatsuro.com.br.meu_bolsista.dto.plano_trabalho.PlanoTrabalhoDTO;
import alveradkatsuro.com.br.meu_bolsista.enumeration.ResponseType;
import alveradkatsuro.com.br.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;
import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import alveradkatsuro.com.br.meu_bolsista.service.plano_trabalho.PlanoTrabalhoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/planoTrabalho")
public class PlanoTrabalhoController {

    private final ModelMapper mapper;

    private final PlanoTrabalhoService planoTrabalhoService;

    @GetMapping
    public Page<PlanoTrabalhoDTO> findAll(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size,
            @RequestParam(defaultValue = "ASC", required = false) Direction direction) {
         return planoTrabalhoService.findAll(page, size, direction).map(e -> mapper.map(e, PlanoTrabalhoDTO.class));
    }

    @GetMapping(value = "/{id}")
    public PlanoTrabalhoDTO findByID(@PathVariable Integer id) {
        return mapper.map(planoTrabalhoService.findById(id), PlanoTrabalhoDTO.class);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseType> save(@RequestBody PlanoTrabalhoDTO planoTrabalhoDTO,
            @CurrentUser UsuarioModel usuario) {
        PlanoTrabalhoModel planoTrabalho = mapper.map(planoTrabalhoDTO, PlanoTrabalhoModel.class);
        planoTrabalho.setLider(usuario);
        planoTrabalho = planoTrabalhoService.save(planoTrabalho);


        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand(planoTrabalho.getId()).toUri()).body(ResponseType.SUCESS_SAVE);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteByID(@PathVariable Integer id) {
        planoTrabalhoService.deleteById(id);
    }

}

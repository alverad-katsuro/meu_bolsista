package alveradkatsuro.com.br.meu_bolsista.service.quadro;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import alveradkatsuro.com.br.meu_bolsista.dto.quadro.painel.QuadroPainelDTO;
import alveradkatsuro.com.br.meu_bolsista.repository.quadro.QuadroRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuadroService {

    private final QuadroRepository quadroRepository;

    private final ModelMapper mapper;

    public Page<QuadroPainelDTO> findAllForPanel(Integer page, Integer size, Direction direction) {
        return quadroRepository.findAllPainelProjection(PageRequest.of(page, size, direction, "id"))
                .map(e -> mapper.map(e, QuadroPainelDTO.class));
    }

    public boolean pesquisadorNoQuadro(String pesquisadorId, Integer quadroId) {
        return quadroRepository.pesquisadorNoQuadro(pesquisadorId, quadroId);
    }

    public Page<QuadroPainelDTO> findAllForPanelAndUserIn(String usuarioId, Integer page, Integer size,
            Direction direction) {
        return quadroRepository.findAllForPanelAndUserIn(usuarioId, PageRequest.of(page, size, direction, "id"))
                .map(e -> mapper.map(e, QuadroPainelDTO.class));
    }
}

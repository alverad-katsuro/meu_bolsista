package alveradkatsuro.com.br.meu_bolsista.service.plano_trabalho;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.multipart.MultipartFile;

import alveradkatsuro.com.br.meu_bolsista.dto.plano_trabalho.PlanoTrabalhoCreateDTO;
import alveradkatsuro.com.br.meu_bolsista.dto.plano_trabalho.PlanoTrabalhoDTO;
import alveradkatsuro.com.br.meu_bolsista.exceptions.NotFoundException;
import alveradkatsuro.com.br.meu_bolsista.exceptions.UnsubmittedReportException;
import alveradkatsuro.com.br.meu_bolsista.model.plano_trabalho.PlanoTrabalhoModel;

public interface PlanoTrabalhoService {

	public PlanoTrabalhoModel findById(Integer id);

	public Page<PlanoTrabalhoModel> findAll(Integer page, Integer size, Direction direction);

	public PlanoTrabalhoModel save(String liderId, PlanoTrabalhoCreateDTO planoTrabalhoCreateDTO, MultipartFile arquivo)
			throws IOException;

	public PlanoTrabalhoModel update(PlanoTrabalhoCreateDTO planoTrabalhoCreateDTO,
			MultipartFile arquivo) throws NotFoundException, IOException;

	public void deleteById(Integer id);

	public boolean relatorioSubmetido(Integer planoTrabalhoId);

	public void updateFinalizar(boolean isFinalizado, Integer planoTrabalhoId) throws UnsubmittedReportException;

	public boolean isUsuarioNoPlano(Integer planoTrabalhoId, String usuarioId);

	public void submeterRelatorio(Integer planoTrabalhoId, MultipartFile arquivo) throws IOException;

}

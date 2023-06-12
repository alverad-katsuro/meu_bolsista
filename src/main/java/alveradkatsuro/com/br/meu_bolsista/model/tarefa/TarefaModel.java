package alveradkatsuro.com.br.meu_bolsista.model.tarefa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import alveradkatsuro.com.br.meu_bolsista.model.usuario.UsuarioModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class TarefaModel {

    @Id
    private String id;
    
    private TarefaModel pai;

    private LocalDate prazo;

    private String desricao;

    private Boolean concluida;

    private Integer cargaHoraria;

    private List<String> etiquetas;

    private UsuarioModel responsavel;

    private List<AtividadesDocument> atividades;

    private List<ImpedimentosDocument> impedimentos;

}

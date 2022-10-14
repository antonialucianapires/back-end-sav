package br.com.escola.sav.dto.request.periodo;

import br.com.escola.sav.dto.request.periodo.subperiodo.SubperiodoRequestDTO;
import br.com.escola.sav.entities.periodo.Periodo;
import br.com.escola.sav.util.validador.UniqueValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PeriodoRequestDTO implements Serializable {

    private static final long serialVersionUID = 1972131416413407202L;

    @JsonProperty("nome_periodo")
    @NotBlank(message = "nome do período não pode ser vazio")
    @UniqueValue(domainClass = Periodo.class, fieldName = "nome_periodo", message = "Já existe um período com este nome")
    private String nomePeriodo;
    @JsonProperty("tipo_periodo")
    @NotNull(message = "tipo do período não pode ser vazio")
    private Integer tipoPeriodo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "data de início não pode ser vazia")
    @JsonProperty("data_inicio")
    private Date dataInicio;
    @JsonProperty("data_fim")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "data fim não pode ser vazia")
    private Date dataFim;
    private List<SubperiodoRequestDTO> subperiodos = new ArrayList<>();

}

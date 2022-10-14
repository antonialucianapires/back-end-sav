package br.com.escola.sav.dto.request.periodo.subperiodo;

import br.com.escola.sav.entities.periodo.Periodo;
import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import br.com.escola.sav.util.validador.UniqueValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubperiodoRequestDTO implements Serializable {

    private static final long serialVersionUID = -8230826326759332892L;

    @JsonProperty("id_subperiodo")
    private Integer idSubperiodo;
    @JsonProperty("nome_subperiodo")
    @UniqueValue(domainClass = SubPeriodo.class, fieldName = "nome_subperiodo", message = "Já existe um Subperíodo com este nome")
    private String nomeSubperiodo;
    @JsonProperty("codigo_periodo")
    private Integer codigoPeriodo;
    @JsonProperty("data_inicio")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataInicio;
    @JsonProperty("data_fim")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataFim;
}

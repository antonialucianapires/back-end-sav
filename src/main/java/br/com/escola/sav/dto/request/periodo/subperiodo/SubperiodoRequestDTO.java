package br.com.escola.sav.dto.request.periodo.subperiodo;

import br.com.escola.sav.entities.periodo.Periodo;
import br.com.escola.sav.util.validador.UniqueValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@EqualsAndHashCode
public class SubperiodoRequestDTO implements Serializable {

    private static final long serialVersionUID = -8230826326759332892L;

    @UniqueValue(domainClass = Periodo.class, fieldName = "nome", message = "Já existe um Subperíodo com este nome")
    private String nomeSubperiodo;
    private Integer codigoPeriodo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataFim;
}

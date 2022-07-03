package br.com.escola.sav.dto.request.periodo;

import br.com.escola.sav.entities.periodo.Periodo;
import br.com.escola.sav.util.validador.UniqueValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@EqualsAndHashCode
public class PeriodoRequestDTO implements Serializable {

    private static final long serialVersionUID = 1972131416413407202L;

    @UniqueValue(domainClass = Periodo.class, fieldName = "nome")
    private String nomePeriodo;
    private int tipoPeriodo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataFim;

}

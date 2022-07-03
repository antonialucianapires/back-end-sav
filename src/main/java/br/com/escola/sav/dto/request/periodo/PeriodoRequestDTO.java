package br.com.escola.sav.dto.request.periodo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@EqualsAndHashCode
public class PeriodoRequestDTO implements Serializable {

    private static final long serialVersionUID = 1972131416413407202L;

    private String nomePeriodo;
    private int tipoPeriodo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataFim;

}

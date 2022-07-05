package br.com.escola.sav.dto.response.periodo;

import br.com.escola.sav.entities.periodo.Periodo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodoResponseDTO implements Serializable {

    private static final long serialVersionUID = 3727934978388786874L;

    private int id;
    private String nomePeriodo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataFim;

    public PeriodoResponseDTO(Periodo periodo) {
        this.id = periodo.getId();
        this.nomePeriodo = periodo.getNome();
        this.dataInicio = periodo.getDataInicio();
        this.dataFim = periodo.getDataFim();
    }

}

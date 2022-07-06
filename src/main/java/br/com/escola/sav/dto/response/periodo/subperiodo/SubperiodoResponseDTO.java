package br.com.escola.sav.dto.response.periodo.subperiodo;

import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
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
public class SubperiodoResponseDTO implements Serializable {

    private static final long serialVersionUID = -3112431951813704717L;
    private int id;
    private String nomeSubperiodo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataFim;
    
    public SubperiodoResponseDTO(SubPeriodo subPeriodo) {
        this.id = subPeriodo.getId();
        this.nomeSubperiodo = subPeriodo.getNome();
        this.dataInicio = subPeriodo.getDataInicio();
        this.dataFim = subPeriodo.getDataFim();
    }
    
}

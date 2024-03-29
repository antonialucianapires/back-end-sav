package br.com.escola.sav.dto.response.periodo.subperiodo;

import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("nome_subperiodo")
    private String nomeSubperiodo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("data_inicio")
    private Date dataInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("data_fim")
    private Date dataFim;
    
    public SubperiodoResponseDTO(SubPeriodo subPeriodo) {
        this.id = subPeriodo.getId();
        this.nomeSubperiodo = subPeriodo.getNome();
        this.dataInicio = subPeriodo.getDataInicio();
        this.dataFim = subPeriodo.getDataFim();
    }
    
}

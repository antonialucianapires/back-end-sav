package br.com.escola.sav.dto.response.periodo;

import br.com.escola.sav.entities.periodo.Periodo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.io.Serializable;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PeriodoResponseDTO implements Serializable {

    private static final long serialVersionUID = 3727934978388786874L;

    private int id;
    private String nomePeriodo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataFim;

    private String status;

    public PeriodoResponseDTO(Periodo periodo) {
        this.id = periodo.getId();
        this.nomePeriodo = periodo.getNome();
        this.dataInicio = periodo.getDataInicio();
        this.dataFim = periodo.getDataFim();
        this.status = gerarStatus();
    }

    private String gerarStatus() {

        Date dataAtual = new Date();

        String statusPeriodo = null;

        Interval intervalo = new Interval(new DateTime(dataInicio), new DateTime(dataFim));

        if (intervalo.containsNow()) {
            statusPeriodo = "andamento";
        } else if (dataAtual.before(dataInicio)) {
            statusPeriodo = "não iniciado";
        } else if (dataAtual.after(dataFim)) {
            statusPeriodo = "encerrado";
        }

        return statusPeriodo;

    }

}

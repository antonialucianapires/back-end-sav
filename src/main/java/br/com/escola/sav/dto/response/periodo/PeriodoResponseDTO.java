package br.com.escola.sav.dto.response.periodo;

import br.com.escola.sav.dto.response.periodo.subperiodo.SubperiodoResponseDTO;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PeriodoResponseDTO implements Serializable {

    private static final long serialVersionUID = 3727934978388786874L;

    private int id;
    private String nomePeriodo;

    private String tipoPeriodo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataFim;

    private String status;

    private List<SubperiodoResponseDTO> subperiodos = new ArrayList<>();

    public PeriodoResponseDTO(Periodo periodo) {
        this.id = periodo.getId();
        this.nomePeriodo = periodo.getNome();
        this.dataInicio = periodo.getDataInicio();
        this.dataFim = periodo.getDataFim();
        this.status = gerarStatus();
    }

    public PeriodoResponseDTO(Periodo periodo, boolean comSubperiodos) {
        this.id = periodo.getId();
        this.nomePeriodo = periodo.getNome();
        this.dataInicio = periodo.getDataInicio();
        this.dataFim = periodo.getDataFim();
        this.status = gerarStatus();
        this.tipoPeriodo = periodo.getTipoPeriodo().getNome();

        if(comSubperiodos) {
            this.subperiodos = periodo.getSubperiodos().stream().map(SubperiodoResponseDTO::new).collect(Collectors.toList());
        } else {
            this.subperiodos = null;
        }
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

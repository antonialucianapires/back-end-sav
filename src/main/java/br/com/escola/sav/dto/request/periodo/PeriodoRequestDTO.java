package br.com.escola.sav.dto.request.periodo;

import br.com.escola.sav.dto.request.periodo.subperiodo.SubperiodoRequestDTO;
import br.com.escola.sav.entities.periodo.Periodo;
import br.com.escola.sav.util.validador.UniqueValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public class PeriodoRequestDTO implements Serializable {

    private static final long serialVersionUID = 1972131416413407202L;

    @NotBlank(message = "nome do período não pode ser vazio")
    @UniqueValue(domainClass = Periodo.class, fieldName = "nome", message = "Já existe um período com este nome")
    private String nomePeriodo;
    @NotNull(message = "tipo do período não pode ser vazio")
    private Integer tipoPeriodo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "data de início não pode ser vazia")
    private Date dataInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "data fim não pode ser vazia")
    private Date dataFim;
    private List<SubperiodoRequestDTO> subperiodos;

}

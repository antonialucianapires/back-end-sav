package br.com.escola.sav.dto.request.periodo.tipo;

import br.com.escola.sav.entities.periodo.tipo.TipoPeriodo;
import br.com.escola.sav.util.validador.UniqueValue;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TipoPeriodoRequestDTO implements Serializable {

    private static final long serialVersionUID = -33362388894360785L;

    @UniqueValue(domainClass = TipoPeriodo.class, fieldName = "nome_periodo", message = "Já existe um tipo de período com este nome")
    private String nomePeriodo;
}

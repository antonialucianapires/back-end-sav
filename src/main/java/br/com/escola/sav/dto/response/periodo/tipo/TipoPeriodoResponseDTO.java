package br.com.escola.sav.dto.response.periodo.tipo;

import br.com.escola.sav.entities.periodo.tipo.TipoPeriodo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPeriodoResponseDTO implements Serializable {

    private static final long serialVersionUID = 8661321140352478540L;

    private int id;
    private String nome;

    public TipoPeriodoResponseDTO(TipoPeriodo tipoPeriodo) {
        this.id = tipoPeriodo.getId();
        this.nome = tipoPeriodo.getNome();
    }
}

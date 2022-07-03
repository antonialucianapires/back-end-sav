package br.com.escola.sav.enums.periodo;

import lombok.Getter;

@Getter
public enum TipoPeriodoEnum {

    BIMESTRAL(1),
    TRIMESTRAL(2),
    SEMESTRAL(3);

    private final int codigo;

    TipoPeriodoEnum(int codigo) {
        this.codigo = codigo;
    }
}

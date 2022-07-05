package br.com.escola.sav.exception.handler;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Error {
    private String field;
    private String message;
}

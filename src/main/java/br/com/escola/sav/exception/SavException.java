package br.com.escola.sav.exception;

public class SavException extends RuntimeException{

    private static final long serialVersionUID = -6624581414063287978L;

    public SavException(String message) {
        super(message);
    }
}

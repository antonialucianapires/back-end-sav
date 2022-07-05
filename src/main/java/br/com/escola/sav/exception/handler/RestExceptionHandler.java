package br.com.escola.sav.exception.handler;

import br.com.escola.sav.dto.request.periodo.ResultView;
import br.com.escola.sav.exception.ObjectNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResultView<Void>> handle(IllegalArgumentException illegalArgumentException) {
        ResultView<Void> resultView = ResultView.<Void>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(illegalArgumentException.getMessage())
                .build();

        return new ResponseEntity<>(resultView,HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFound.class)
    public ResponseEntity<ResultView<Void>> handle(ObjectNotFound objectNotFound) {
        ResultView<Void> resultView = ResultView.<Void>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(objectNotFound.getMessage())
                .build();

        return new ResponseEntity<>(resultView,HttpStatus.NOT_FOUND);
    }

}

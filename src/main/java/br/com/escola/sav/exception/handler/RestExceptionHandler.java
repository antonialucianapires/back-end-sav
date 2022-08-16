package br.com.escola.sav.exception.handler;

import br.com.escola.sav.dto.request.periodo.ResultView;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.exception.SavException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestControllerAdvice
public class RestExceptionHandler {

    public static final Logger logger = Logger.getLogger(RestExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultView<Void>> handle(Exception exception) {
        logger.info(exception.getMessage());
        ResultView<Void> resultView = ResultView.<Void>builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Falha ao realizar operação.")
                .build();

        return new ResponseEntity<>(resultView,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SavException.class)
    public ResponseEntity<ResultView<Void>> handle(SavException savException) {
        logger.info(savException.getMessage());
        ResultView<Void> resultView = ResultView.<Void>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(savException.getMessage())
                .build();

        return new ResponseEntity<>(resultView,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectNotFound.class)
    public ResponseEntity<ResultView<Void>> handle(ObjectNotFound objectNotFound) {
        logger.info(objectNotFound.getMessage());
        ResultView<Void> resultView = ResultView.<Void>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(objectNotFound.getMessage())
                .build();

        return new ResponseEntity<>(resultView,HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultView<List<Error>>> handle(MethodArgumentNotValidException methodArgumentNotValidException) {

        List<Error> errors = new ArrayList<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(err -> {
            Error error = Error.builder()
                    .field(err.getField())
                    .message(err.getDefaultMessage())
                    .build();

            errors.add(error);
        });

        logger.info(methodArgumentNotValidException.getMessage());
        ResultView<List<Error>> resultView = ResultView.<List<Error>>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Existem valores de entrada inválidos")
                .payload(errors)
                .build();

        return new ResponseEntity<>(resultView,HttpStatus.BAD_REQUEST);
    }
}

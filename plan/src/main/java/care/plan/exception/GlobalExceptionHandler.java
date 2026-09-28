package care.plan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DoseAlreadyFinalizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDoseAlreadyFinalized(
            DoseAlreadyFinalizedException exception) {

        return exception.getMessage();
    }

    @ExceptionHandler(DoseLogAlreadyFinalizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDoseLogAlreadyFinalized(
            DoseLogAlreadyFinalizedException exception) {

        return exception.getMessage();
    }
}
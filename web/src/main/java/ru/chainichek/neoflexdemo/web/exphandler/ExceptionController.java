package ru.chainichek.neoflexdemo.web.exphandler;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.chainichek.neoflexdemo.web.exphandler.dto.ErrorMessage;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController {
    private static final Logger LOGGER = LogManager.getLogger(ExceptionController.class);

    private void logError(final Exception exception,
                          final ErrorMessage errorMessage) {
        String message = exception.getClass().getSimpleName() + ":" +
                errorMessage.toString();
        LOGGER.error(message, exception);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgumentException(final IllegalArgumentException exception,
                                                          final HttpServletRequest request) {
        final ErrorMessage message = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI());
        logError(exception, message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> otherException(final RuntimeException exception,
                                                          final HttpServletRequest request) {
        final ErrorMessage message = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI());
        logError(exception, message);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(message);
    }
}

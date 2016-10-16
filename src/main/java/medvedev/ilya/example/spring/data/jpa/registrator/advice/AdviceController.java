package medvedev.ilya.example.spring.data.jpa.registrator.advice;

import medvedev.ilya.example.spring.data.jpa.registrator.advice.response.ErrorsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AdviceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdviceController.class);

    private static ResponseEntity<ErrorsResponse> logHandler(
            final HttpStatus httpStatus,
            final List<String> errors,
            final Exception exception
    ) {
        final UUID code = UUID.randomUUID();
        final String status = httpStatus.getReasonPhrase();

        LOGGER.warn("{} ({})", status, code, exception);

        final ErrorsResponse errorsResponse = new ErrorsResponse(code, errors);

        return ResponseEntity.status(httpStatus)
                .body(errorsResponse);
    }

    private ResponseEntity<ErrorsResponse> errorsResponseByError(
            final HttpStatus httpStatus,
            final String error,
            final Exception exception
    ) {
        final List<String> errors = Collections.singletonList(error);

        return logHandler(httpStatus, errors, exception);
    }

    private ResponseEntity<ErrorsResponse> errorsResponseByException(
            final HttpStatus httpStatus,
            final Exception exception
    ) {
        final String error = exception.getMessage();

        return errorsResponseByError(httpStatus, error, exception);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorsResponse> httpMessageNotReadableException(
            final HttpMessageNotReadableException exception
    ) {
        return errorsResponseByError(HttpStatus.BAD_REQUEST, "Request body is wrong", exception);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorsResponse> methodArgumentNotValidException(
            final MethodArgumentNotValidException exception
    ) {
        final List<String> errors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return logHandler(HttpStatus.BAD_REQUEST, errors, exception);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorsResponse> noHandlerFoundException(
            final NoHandlerFoundException noHandlerFoundException
    ) {
        return errorsResponseByException(HttpStatus.NOT_FOUND, noHandlerFoundException);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorsResponse> httpRequestMethodNotSupportedException(
            final HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException
    ) {
        return errorsResponseByException(HttpStatus.METHOD_NOT_ALLOWED, httpRequestMethodNotSupportedException);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorsResponse> httpMediaTypeNotSupportedException(
            final HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException
    ) {
        return errorsResponseByException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, httpMediaTypeNotSupportedException);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorsResponse> unknownException(final Exception exception) {
        return errorsResponseByError(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error", exception);
    }
}

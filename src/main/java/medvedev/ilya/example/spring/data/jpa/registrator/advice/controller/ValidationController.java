package medvedev.ilya.example.spring.data.jpa.registrator.advice.controller;

import medvedev.ilya.example.spring.data.jpa.registrator.advice.response.ErrorsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationController.class);

    private static ErrorsResponse logHandler(
            final HttpStatus httpStatus,
            final List<String> errors,
            final Exception exception
    ) {
        final String status = httpStatus.getReasonPhrase();

        LOGGER.warn(status, exception);

        return new ErrorsResponse(errors);
    }

    private ErrorsResponse errorsResponseByError(
            final HttpStatus httpStatus,
            final String error,
            final Exception exception
    ) {
        final List<String> errors = Collections.singletonList(error);

        return logHandler(httpStatus, errors, exception);
    }

    private ErrorsResponse errorsResponseByException(final HttpStatus httpStatus, final Exception exception) {
        final String error = exception.getMessage();

        return errorsResponseByError(httpStatus, error, exception);
    }

    @ExceptionHandler
    public ErrorsResponse httpMessageNotReadableException(final HttpMessageNotReadableException exception) {
        return errorsResponseByError(HttpStatus.BAD_REQUEST, "Required request body is wrong", exception);
    }

    @ExceptionHandler
    public ErrorsResponse methodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final List<String> errors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return logHandler(HttpStatus.BAD_REQUEST, errors, exception);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsResponse noHandlerFoundException(final NoHandlerFoundException exception) {
        return errorsResponseByException(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorsResponse httpMediaTypeNotSupportedException(final HttpRequestMethodNotSupportedException exception) {
        return errorsResponseByException(HttpStatus.METHOD_NOT_ALLOWED, exception);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorsResponse httpMediaTypeNotSupportedException(final HttpMediaTypeNotSupportedException exception) {
        return errorsResponseByException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception);
    }
}

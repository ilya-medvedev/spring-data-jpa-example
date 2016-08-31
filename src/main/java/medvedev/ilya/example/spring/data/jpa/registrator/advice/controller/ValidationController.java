package medvedev.ilya.example.spring.data.jpa.registrator.advice.controller;

import medvedev.ilya.example.spring.data.jpa.registrator.advice.response.ErrorsResponse;
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
    private ErrorsResponse errorsResponseByError(final String error) {
        final List<String> errors = Collections.singletonList(error);

        return new ErrorsResponse(errors);
    }

    private ErrorsResponse errorsResponseByException(final Exception exception) {
        final String error = exception.getMessage();

        return errorsResponseByError(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorsResponse httpMessageNotReadableException() {
        return errorsResponseByError("Required request body is wrong");
    }

    @ExceptionHandler
    public ErrorsResponse methodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final List<String> errors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ErrorsResponse(errors);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsResponse noHandlerFoundException(final NoHandlerFoundException exception) {
        return errorsResponseByException(exception);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorsResponse httpMediaTypeNotSupportedException(final HttpRequestMethodNotSupportedException exception) {
        return errorsResponseByException(exception);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorsResponse httpMediaTypeNotSupportedException(final HttpMediaTypeNotSupportedException exception) {
        return errorsResponseByException(exception);
    }
}

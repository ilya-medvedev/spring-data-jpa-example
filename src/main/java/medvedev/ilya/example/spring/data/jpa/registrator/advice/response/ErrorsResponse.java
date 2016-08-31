package medvedev.ilya.example.spring.data.jpa.registrator.advice.response;

import java.util.Collections;
import java.util.List;

public class ErrorsResponse {
    private final List<String> errors;

    public ErrorsResponse(final List<String> errors) {
        this.errors = errors;
    }


    public static ErrorsResponse byError(final String error) {
        final List<String> errors = Collections.singletonList(error);

        return new ErrorsResponse(errors);
    }

    public List<String> getErrors() {
        return errors;
    }
}

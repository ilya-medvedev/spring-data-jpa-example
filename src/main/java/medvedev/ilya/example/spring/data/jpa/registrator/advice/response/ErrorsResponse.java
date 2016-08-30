package medvedev.ilya.example.spring.data.jpa.registrator.advice.response;

import java.util.List;

public class ErrorsResponse {
    private final List<String> errors;

    public ErrorsResponse(final List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}

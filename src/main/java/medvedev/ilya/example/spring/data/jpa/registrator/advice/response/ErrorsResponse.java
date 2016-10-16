package medvedev.ilya.example.spring.data.jpa.registrator.advice.response;

import java.util.List;
import java.util.UUID;

public class ErrorsResponse {
    private final UUID code;
    private final List<String> errors;

    public ErrorsResponse(final UUID code, final List<String> errors) {
        this.code = code;
        this.errors = errors;
    }

    public UUID getCode() {
        return code;
    }

    public List<String> getErrors() {
        return errors;
    }
}

package medvedev.ilya.example.spring.data.jpa.registrator.advice.response;

public class MessageResponse {
    private final String message;

    public MessageResponse(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

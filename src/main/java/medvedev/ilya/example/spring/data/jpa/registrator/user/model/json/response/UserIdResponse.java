package medvedev.ilya.example.spring.data.jpa.registrator.user.model.json.response;

public class UserIdResponse {
    private final String id;

    public UserIdResponse(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

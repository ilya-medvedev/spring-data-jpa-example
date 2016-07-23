package medvedev.ilya.example.spring.data.jpa.registrator.user.model.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserIdResponse {
    @JsonProperty("uid")
    private final String id;

    public UserIdResponse(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

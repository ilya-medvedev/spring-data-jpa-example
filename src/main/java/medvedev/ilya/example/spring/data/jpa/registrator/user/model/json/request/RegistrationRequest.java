package medvedev.ilya.example.spring.data.jpa.registrator.user.model.json.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.NotNull;

@JsonDeserialize(builder = RegistrationRequest.Builder.class)
public class RegistrationRequest {
    @NotNull
    private final String password;

    private final String nick;

    private RegistrationRequest(final Builder builder) {
        password = builder.password;
        nick = builder.nick;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String password;
        private String nick;

        public Builder setPassword(final String password) {
            this.password = password;

            return this;
        }

        public Builder setNick(final String nick) {
            this.nick = nick;

            return this;
        }

        public RegistrationRequest build() {
            return new RegistrationRequest(this);
        }
    }
}

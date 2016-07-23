package medvedev.ilya.example.spring.data.jpa.registrator.user.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private final String id = null;

    @Column(nullable = false)
    private final String password;

    private final String nick;

    private User(final Builder builder) {
        password = builder.password;
        nick = builder.nick;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getNick() {
        return nick;
    }

    public static class Builder {
        private String password;
        private String nick;

        public Builder setPassword(String password) {
            this.password = password;

            return this;
        }

        public Builder setNick(String nick) {
            this.nick = nick;

            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

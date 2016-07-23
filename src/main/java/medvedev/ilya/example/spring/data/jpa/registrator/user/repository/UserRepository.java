package medvedev.ilya.example.spring.data.jpa.registrator.user.repository;

import medvedev.ilya.example.spring.data.jpa.registrator.user.model.jpa.User;
import org.springframework.data.repository.Repository;

import java.util.concurrent.CompletableFuture;

public interface UserRepository extends Repository<User, String> {
    CompletableFuture<User> save(final User user);
}

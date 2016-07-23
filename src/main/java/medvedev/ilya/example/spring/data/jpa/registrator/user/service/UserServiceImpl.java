package medvedev.ilya.example.spring.data.jpa.registrator.user.service;

import medvedev.ilya.example.spring.data.jpa.registrator.user.model.json.request.RegistrationRequest;
import medvedev.ilya.example.spring.data.jpa.registrator.user.model.json.response.UserIdResponse;
import medvedev.ilya.example.spring.data.jpa.registrator.user.model.jpa.User;
import medvedev.ilya.example.spring.data.jpa.registrator.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repository;

    public UserServiceImpl(final UserRepository repository) {
        this.repository = repository;
    }

    private static UserIdResponse postRegistrationHandler(final User user) {
        final String id = user.getId();
        final String nick = user.getNick();

        LOGGER.debug("A new user \"{}\" has been registered with id \"{}\"", nick, id);

        return new UserIdResponse(id);
    }

    @Override
    public CompletableFuture<UserIdResponse> registration(final RegistrationRequest registrationRequest) {
        final String password = registrationRequest.getPassword();
        final String hash = UserServiceUtil.passwordHash(password);

        final String nick = registrationRequest.getNick();

        final User user = new User.Builder()
                .setPassword(hash)
                .setNick(nick)
                .build();

        return repository.save(user)
                .thenApply(UserServiceImpl::postRegistrationHandler);
    }
}

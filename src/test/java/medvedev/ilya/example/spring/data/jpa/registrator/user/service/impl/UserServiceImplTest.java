package medvedev.ilya.example.spring.data.jpa.registrator.user.service.impl;

import medvedev.ilya.example.spring.data.jpa.registrator.user.model.jpa.User;
import medvedev.ilya.example.spring.data.jpa.registrator.user.matcher.ObjectMatcher;
import medvedev.ilya.example.spring.data.jpa.registrator.user.model.json.request.RegistrationRequest;
import medvedev.ilya.example.spring.data.jpa.registrator.user.repository.UserRepository;
import medvedev.ilya.example.spring.data.jpa.registrator.user.service.UserServiceUtil;
import medvedev.ilya.example.spring.data.jpa.registrator.user.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class UserServiceImplTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository);

    @Test
    public void registrationTest() throws ExecutionException, InterruptedException {
        final String id = "1";

        final User mockUser = Mockito.mock(User.class);

        Mockito.doReturn(id)
                .when(mockUser)
                .getId();

        final CompletableFuture<User> completableFuture = CompletableFuture.completedFuture(mockUser);

        final String pass = "pass";
        final String hash = UserServiceUtil.passwordHash(pass);
        final String nick = "nick";

        final User user = new User.Builder()
                .setPassword(hash)
                .setNick(nick)
                .build();

        Mockito.doReturn(completableFuture)
                .when(userRepository)
                .save(ObjectMatcher.eq((user)));

        final RegistrationRequest registrationRequest = new RegistrationRequest.Builder()
                .setPassword(pass)
                .setNick(nick)
                .build();

        final String actual = userServiceImpl.registration(registrationRequest)
                .get()
                .getId();

        Assert.assertEquals(id, actual);

        Mockito.verify(userRepository, Mockito.only())
                .save(ObjectMatcher.eq(user));
    }
}

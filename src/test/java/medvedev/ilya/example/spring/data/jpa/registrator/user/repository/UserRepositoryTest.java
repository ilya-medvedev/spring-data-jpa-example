package medvedev.ilya.example.spring.data.jpa.registrator.user.repository;

import medvedev.ilya.example.spring.data.jpa.registrator.user.model.jpa.User;
import medvedev.ilya.example.spring.data.jpa.registrator.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutionException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveTest() throws ExecutionException, InterruptedException {
        final String id = "1";
        final String password = "pass";
        final String nick = "nick";

        final User user = new User.Builder()
                .setPassword(password)
                .setNick(nick)
                .build();

        final User actual = userRepository.save(user)
                .get();

        final String actualId = actual.getId();
        final String actualPassword = actual.getPassword();
        final String actualNick = actual.getNick();

        Assert.assertEquals(id, actualId);
        Assert.assertEquals(password, actualPassword);
        Assert.assertEquals(nick, actualNick);
    }
}
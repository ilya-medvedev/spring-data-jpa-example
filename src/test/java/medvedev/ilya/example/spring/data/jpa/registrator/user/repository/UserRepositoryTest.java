package medvedev.ilya.example.spring.data.jpa.registrator.user.repository;

import medvedev.ilya.example.spring.data.jpa.registrator.user.model.jpa.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
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
package medvedev.ilya.example.spring.data.jpa.registrator.user.service;

import org.junit.Assert;
import org.junit.Test;

public class UserServiceUtilTest {
    @Test
    public void passwordHash() throws Exception {
        final String password = "1";

        final String expected = "c4ca4238a0b923820dcc509a6f75849b";
        final String actual = UserServiceUtil.passwordHash(password);

        Assert.assertEquals(expected, actual);
    }
}
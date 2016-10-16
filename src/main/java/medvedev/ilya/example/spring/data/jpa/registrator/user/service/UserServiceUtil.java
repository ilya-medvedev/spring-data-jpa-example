package medvedev.ilya.example.spring.data.jpa.registrator.user.service;

import org.springframework.util.DigestUtils;

public class UserServiceUtil {
    public static String passwordHash(final String password) {
        final byte[] bytes = password.getBytes();

        return DigestUtils.md5DigestAsHex(bytes);
    }
}

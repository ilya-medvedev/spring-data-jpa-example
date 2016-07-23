package medvedev.ilya.example.spring.data.jpa.registrator.user.service;

import org.springframework.util.DigestUtils;

class UserServiceUtil {
    static String passwordHash(final String password) {
        final byte[] bytes = password.getBytes();

        return DigestUtils.md5DigestAsHex(bytes);
    }
}

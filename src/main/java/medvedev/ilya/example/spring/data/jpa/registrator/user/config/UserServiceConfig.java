package medvedev.ilya.example.spring.data.jpa.registrator.user.config;

import medvedev.ilya.example.spring.data.jpa.registrator.user.service.UserService;
import medvedev.ilya.example.spring.data.jpa.registrator.user.service.impl.UserServiceImpl;
import medvedev.ilya.example.spring.data.jpa.registrator.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfig {
    @Bean
    public UserService userService(final UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }
}

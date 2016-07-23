package medvedev.ilya.example.spring.data.jpa.registrator.user;

import medvedev.ilya.example.spring.data.jpa.registrator.user.model.json.request.RegistrationRequest;
import medvedev.ilya.example.spring.data.jpa.registrator.user.model.json.response.UserIdResponse;
import medvedev.ilya.example.spring.data.jpa.registrator.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public CompletableFuture<UserIdResponse> registration(
            @RequestBody @Valid final RegistrationRequest registrationRequest
    ) {
        return userService.registration(registrationRequest);
    }
}

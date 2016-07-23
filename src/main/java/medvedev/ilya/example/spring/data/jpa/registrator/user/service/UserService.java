package medvedev.ilya.example.spring.data.jpa.registrator.user.service;

import medvedev.ilya.example.spring.data.jpa.registrator.user.model.json.request.RegistrationRequest;
import medvedev.ilya.example.spring.data.jpa.registrator.user.model.json.response.UserIdResponse;

import java.util.concurrent.CompletableFuture;

public interface UserService {
    CompletableFuture<UserIdResponse> registration(final RegistrationRequest registrationRequest);
}

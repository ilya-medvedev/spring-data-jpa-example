package medvedev.ilya.example.spring.data.jpa.registrator.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import medvedev.ilya.example.spring.data.jpa.registrator.user.model.json.response.UserIdResponse;
import medvedev.ilya.example.spring.data.jpa.registrator.user.service.UserService;
import medvedev.ilya.example.spring.data.jpa.registrator.user.matcher.ObjectMatcher;
import medvedev.ilya.example.spring.data.jpa.registrator.user.model.json.request.RegistrationRequest;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.CompletableFuture;

public class UserControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserService userService = Mockito.mock(UserService.class);

    private final MockMvc mockMvc;

    public UserControllerTest() {
        final UserController userController = new UserController(userService);

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    private ResultActions registrationTest(
            final RegistrationRequest registrationRequest,
            final UserIdResponse userIdResponse
    ) throws Exception {
        final CompletableFuture<UserIdResponse> completableFuture = CompletableFuture.completedFuture(userIdResponse);

        Mockito.doReturn(completableFuture)
                .when(userService)
                .registration(ObjectMatcher.eq(registrationRequest));

        final String input = objectMapper.writeValueAsString(registrationRequest);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(input);

        final ResultActions resultActions = mockMvc.perform(requestBuilder);

        final boolean async = resultActions.andReturn()
                .getRequest()
                .isAsyncStarted();

        if (!async) {
            return resultActions;
        }

        final MvcResult mvcResult = resultActions
                .andExpect(MockMvcResultMatchers.request()
                        .asyncStarted())
                .andReturn();

        return mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult));
    }

    @Test
    public void registrationTest() throws Exception {
        final RegistrationRequest registrationRequest = new RegistrationRequest.Builder()
                .setPassword("pass")
                .setNick("nick")
                .build();

        final UserIdResponse userIdResponse = new UserIdResponse("1");

        final String output = objectMapper.writeValueAsString(userIdResponse);

        registrationTest(registrationRequest, userIdResponse)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content()
                        .json(output));

        Mockito.verify(userService, Mockito.only())
                .registration(ObjectMatcher.eq(registrationRequest));
    }

    @Test
    public void registrationWithoutNickTest() throws Exception {
        final RegistrationRequest registrationRequest = new RegistrationRequest.Builder()
                .setPassword("pass")
                .build();

        final UserIdResponse userIdResponse = new UserIdResponse("1");

        final String output = objectMapper.writeValueAsString(userIdResponse);

        registrationTest(registrationRequest, userIdResponse)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content()
                        .json(output));

        Mockito.verify(userService, Mockito.only())
                .registration(ObjectMatcher.eq(registrationRequest));
    }

    @Test
    public void registrationWithoutPassTest() throws Exception {
        final RegistrationRequest registrationRequest = new RegistrationRequest.Builder()
                .setNick("nick")
                .build();

        final UserIdResponse userIdResponse = new UserIdResponse("1");

        registrationTest(registrationRequest, userIdResponse)
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());
    }
}
package medvedev.ilya.example.spring.data.jpa.registrator.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import medvedev.ilya.example.spring.data.jpa.registrator.advice.controller.TestController;
import medvedev.ilya.example.spring.data.jpa.registrator.advice.model.TestRequest;
import medvedev.ilya.example.spring.data.jpa.registrator.advice.response.ErrorsResponse;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

public class AdviceControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MockMvc mockMvc;

    public AdviceControllerTest() {
        final TestController testController = new TestController();
        final AdviceController adviceController = new AdviceController();

        mockMvc = MockMvcBuilders.standaloneSetup(testController)
                .setControllerAdvice(adviceController)
                .build();
    }

    private void test(
            final RequestBuilder requestBuilder,
            final ResultMatcher resultMatcher,
            final String error
    ) throws Exception {
        final List<String> errors = Collections.singletonList(error);
        final ErrorsResponse errorsResponse = new ErrorsResponse(errors);
        final String output = objectMapper.writeValueAsString(errorsResponse);

        mockMvc.perform(requestBuilder)
                .andExpect(resultMatcher)
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content()
                        .json(output));
    }

    private void httpMessageNotReadableExceptionTest(final String content) throws Exception {
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/test")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content);

        final String error = "Request body is wrong";
        final ResultMatcher resultMatcher = MockMvcResultMatchers.status()
                .isBadRequest();

        test(requestBuilder, resultMatcher, error);
    }

    @Test
    public void withoutAdviceTest() throws Exception {
        final TestRequest testRequest = new TestRequest("test");
        final String content = objectMapper.writeValueAsString(testRequest);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/test")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content()
                        .json(content));
    }

    @Test
    public void httpMessageNotReadableExceptionTest1() throws Exception {
        httpMessageNotReadableExceptionTest("");
    }

    @Test
    public void httpMessageNotReadableExceptionTest2() throws Exception {
        httpMessageNotReadableExceptionTest("{");
    }

    @Test
    public void httpMessageNotReadableExceptionTest3() throws Exception {
        httpMessageNotReadableExceptionTest("}");
    }

    @Test
    public void methodArgumentNotValidExceptionTest() throws Exception {
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/test")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{}");

        final String error = "Test can not be null";
        final ResultMatcher resultMatcher = MockMvcResultMatchers.status()
                .isBadRequest();

        test(requestBuilder, resultMatcher, error);
    }

    @Test
    public void httpRequestMethodNotSupportedExceptionTest() throws Exception {
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/test")
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        final String error = "Request method '" + HttpMethod.GET + "' not supported";
        final ResultMatcher resultMatcher = MockMvcResultMatchers.status()
                .isMethodNotAllowed();

        test(requestBuilder, resultMatcher, error);
    }

    @Test
    public void httpMediaTypeNotSupportedExceptionnTest() throws Exception {
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/test")
                .contentType(MediaType.APPLICATION_XML);

        final String error = "Content type '" + MediaType.APPLICATION_XML_VALUE + "' not supported";
        final ResultMatcher resultMatcher = MockMvcResultMatchers.status()
                .isUnsupportedMediaType();

        test(requestBuilder, resultMatcher, error);
    }
}

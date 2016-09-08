package medvedev.ilya.example.spring.data.jpa.registrator.advice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class TestRequest {
    @NotNull(message = "Test can not be null")
    private final String test;

    public TestRequest(@JsonProperty("test") final String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }
}

package medvedev.ilya.example.spring.data.jpa.registrator.advice.model;

import javax.validation.constraints.NotNull;

public class TestRequest {
    @NotNull(message = "Test can not be null")
    public String test;
}

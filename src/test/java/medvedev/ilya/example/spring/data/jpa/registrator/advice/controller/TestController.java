package medvedev.ilya.example.spring.data.jpa.registrator.advice.controller;

import medvedev.ilya.example.spring.data.jpa.registrator.advice.model.TestRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "test")
public class TestController {
    @RequestMapping(method = RequestMethod.POST)
    public TestRequest test(@Valid @RequestBody TestRequest testRequest) {
        return testRequest;
    }
}

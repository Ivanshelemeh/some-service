package com.example.someservice.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    @WithAnonymousUser
    public void test_login_endpoint() {
        var body = this.restTemplate.getForObject("http://localhost:" + port + "/auth/login", String.class);
        ModelAndView view = new ModelAndView();
        view.setViewName(body);
        assertThat(body).isNotNull().isEqualTo(view.getViewName());

    }

}

package com.example.someservice.rest;

import com.example.someservice.repository.PetRepository;
import com.example.someservice.service.impl.PetServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PetServiceImpl service;

    @MockBean
    private PetRepository petRepository;

    @Test
    @SneakyThrows
    void should_not_permit_unauthorized_person() {
        mockMvc.perform(MockMvcRequestBuilders.get("/pet/pets"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "Nina", roles = "AUTHORIZED")
    @DisplayName("account with filled credentials should get access")
    void authorized_user_should_get_all_pets() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pet/pets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @SneakyThrows
    @Test
    @DisplayName("unauthorized person could not create a pet")
    void should_not_deny_create_unauthorized() {
        mockMvc.perform((MockMvcRequestBuilders.post("/pet/make"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json")
                        .param("sex", "Sex of pet")
                        .param("nickName", "Nick of pet"))
                .andExpect(status().isUnauthorized());

    }
}

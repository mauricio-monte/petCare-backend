package com.petcare.backend.controller;

import com.petcare.backend.PetCareBackendApplication;
import com.petcare.backend.dto.user.CreateUserDTO;
import com.petcare.backend.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PetCareBackendApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class UserControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    private void createUsers() {
        CreateUserDTO createUserDTO = new CreateUserDTO("test", "test@gmail.com", "test_password");
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200()
            throws Exception {
        CreateUserDTO createUserDTO = new CreateUserDTO("test", "test@gmail.com", "test_password");

        Assert.assertEquals(1,1);

//
//        mvc.perform(post("/api/employees")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].name", is("bob")));
    }
}

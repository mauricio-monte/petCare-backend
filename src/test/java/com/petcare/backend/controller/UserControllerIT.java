package com.petcare.backend.controller;

import com.petcare.backend.PetCareBackendApplication;
import com.petcare.backend.dto.user.CreateUserDTO;
import com.petcare.backend.repository.UserRepository;
import com.petcare.backend.util.JsonMapperUtil;
import org.junit.Assert;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
    public void testPost()
            throws Exception {

        CreateUserDTO createUserDTO = new CreateUserDTO("test", "test@gmail.com", "test_password");
        String test = JsonMapperUtil.fromObjectToJsonString(createUserDTO);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(test))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("test")));
    }

    @Test
    public void testGet()
            throws Exception {

        CreateUserDTO createUserDTO = new CreateUserDTO("test", "test@gmail.com", "test_password");
        String test = JsonMapperUtil.fromObjectToJsonString(createUserDTO);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(test))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("test")));
    }
}

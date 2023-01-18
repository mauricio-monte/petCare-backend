package com.petcare.backend.controller;

import com.petcare.backend.PetCareBackendApplication;
import com.petcare.backend.domain.User;
import com.petcare.backend.dto.user.CreateUserDTO;
import com.petcare.backend.dto.user.LoginDTO;
import com.petcare.backend.dto.user.LoginReturnDTO;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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

    @Test
    public void testPost()
            throws Exception {

        // User creation test
        CreateUserDTO createUserDTO = new CreateUserDTO("test", "test@gmail.com", "test_password");
        String test = JsonMapperUtil.fromObjectToJsonString(createUserDTO);

        MvcResult postResult = mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(test))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.email", is("test@gmail.com")))
                .andReturn();

        System.out.println(postResult.getResponse().getContentAsString());

        // Get all users test
        User createdUser = JsonMapperUtil.fromJsonStringToObject(postResult.getResponse().getContentAsString(), User.class);
        int createdUserId = Math.toIntExact(createdUser.getId());
        String bearerAuth = loginAndGetToken(createUserDTO);

        MvcResult getAllResult = mvc.perform(get("/users")
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(createdUserId)))
                .andExpect(jsonPath("$[0].name", is("test")))
                .andExpect(jsonPath("$[0].email", is("test@gmail.com")))
                .andReturn();

        System.out.println(getAllResult.getResponse().getContentAsString());

        // Get one user test
        MvcResult getOneResult = mvc.perform(get("/users/" + createdUserId)
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(createdUserId)))
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.email", is("test@gmail.com")))
                .andReturn();

        System.out.println(getOneResult.getResponse().getContentAsString());
        User getOneUser = JsonMapperUtil.fromJsonStringToObject(getOneResult.getResponse().getContentAsString(), User.class);
        Assert.assertEquals(createdUser, getOneUser);


        // Update user test
        User updatedUser = new User("updatedTest", "updated@test.com");
        String updatedUserJson = JsonMapperUtil.fromObjectToJsonString(updatedUser);

        mvc.perform(patch("/users/" + createdUserId)
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(createdUserId)))
                .andExpect(jsonPath("$.name", is(updatedUser.getName())))
                .andExpect(jsonPath("$.email", is(updatedUser.getEmail())));

        MvcResult getUpdatedResult = mvc.perform(get("/users/" + createdUserId)
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(createdUserId)))
                .andExpect(jsonPath("$.name", is(updatedUser.getName())))
                .andExpect(jsonPath("$.email", is(updatedUser.getEmail())))
                .andReturn();

        User getUpdatedUser = JsonMapperUtil.fromJsonStringToObject(getUpdatedResult.getResponse().getContentAsString(), User.class);
        Assert.assertEquals(updatedUser.getName(), getUpdatedUser.getName());
        Assert.assertEquals(updatedUser.getEmail(), getUpdatedUser.getEmail());
    }

    private String loginAndGetToken(CreateUserDTO postDTO) throws Exception {

        LoginDTO loginDTO = new LoginDTO(postDTO.getEmail(), postDTO.getPassword());
        String loginDTOJson = JsonMapperUtil.fromObjectToJsonString(loginDTO);

        MvcResult mvcResult = mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginDTOJson))
                .andReturn();
        mvcResult.getResponse().getContentAsString();
        LoginReturnDTO loginReturnDTO = JsonMapperUtil.fromJsonStringToObject(mvcResult.getResponse().getContentAsString(), LoginReturnDTO.class);
        return "Bearer " + loginReturnDTO.getToken();
    }
}

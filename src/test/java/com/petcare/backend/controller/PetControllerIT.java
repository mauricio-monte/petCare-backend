package com.petcare.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petcare.backend.PetCareBackendApplication;
import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.User;
import com.petcare.backend.dto.pet.CreatePetDTO;
import com.petcare.backend.dto.user.CreateUserDTO;
import com.petcare.backend.dto.user.LoginDTO;
import com.petcare.backend.dto.user.LoginReturnDTO;
import com.petcare.backend.repository.PetRepository;
import com.petcare.backend.repository.UserRepository;
import com.petcare.backend.util.JsonMapperUtil;
import org.junit.Assert;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PetCareBackendApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class PetControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Test
    public void petControllerIntegrationTests()
            throws Exception {

        Pair<Long, String> utilPair = createUserLoginAndGetToken();
        Long userId = utilPair.getFirst();
        String bearerAuth = utilPair.getSecond();

        // Create pet test
        CreatePetDTO createPetDTO = new CreatePetDTO("simas", "www", new Date("26/07/2020"),
                                                     Character.valueOf('m'), Float.valueOf(2), "dog",
                                                "golden retriever", "unknown", userId);

        String createPetJson = JsonMapperUtil.fromObjectToJsonString(createPetDTO);

        MvcResult createPetResult = mvc.perform(post("/pets")
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPetJson))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name", is("simas")))
                        .andExpect(jsonPath("$.race", is("golden retriever")))
                        .andReturn();

        System.out.println(createPetResult.getResponse().getContentAsString());
        Pet createdPet = JsonMapperUtil.fromJsonStringToObject(createPetResult.getResponse().getContentAsString(), Pet.class);
        Assert.assertEquals(new Pet(createPetDTO),createdPet);

    }

    private Pair<Long, String> createUserLoginAndGetToken() throws Exception {
        CreateUserDTO createUserDTO = new CreateUserDTO("test", "test@gmail.com", "test_password");
        String createUserJson = JsonMapperUtil.fromObjectToJsonString(createUserDTO);

        MvcResult postResult = mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUserJson))
                        .andReturn();

        User createdUser = JsonMapperUtil.fromJsonStringToObject(postResult.getResponse().getContentAsString(), User.class);
        LoginDTO loginDTO = new LoginDTO(createUserDTO.getEmail(), createUserDTO.getPassword());
        String loginDTOJson = JsonMapperUtil.fromObjectToJsonString(loginDTO);

        MvcResult mvcResult = mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginDTOJson))
                        .andReturn();

        mvcResult.getResponse().getContentAsString();
        LoginReturnDTO loginReturnDTO = JsonMapperUtil.fromJsonStringToObject(mvcResult.getResponse().getContentAsString(), LoginReturnDTO.class);
        String bearerToken = "Bearer " + loginReturnDTO.getToken();

        Pair<Long, String> result = Pair.of(createdUser.getId(), bearerToken);

        return result;
    }
}

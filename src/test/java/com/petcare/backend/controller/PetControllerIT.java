package com.petcare.backend.controller;

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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private PetRepository petRepository;

    @Test
    public void petControllerIntegrationTests()
            throws Exception {
    
        Pair<Long, String> utilPair = createUserLoginAndGetToken();
        Long userId = utilPair.getFirst();
        String bearerAuth = utilPair.getSecond();

        // Create pet test
        CreatePetDTO createPetDTO = getTestPetDTO(userId);
        String createPetJson = JsonMapperUtil.fromObjectToJsonString(createPetDTO);

        MvcResult createPetResult = mvc.perform(post("/pets")
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPetJson))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name", is(createPetDTO.getName())))
                        .andExpect(jsonPath("$.race", is(createPetDTO.getRace())))
                        .andReturn();

        System.out.println(createPetResult.getResponse().getContentAsString());
        Pet createdPet = JsonMapperUtil.fromJsonStringToObject(createPetResult.getResponse().getContentAsString(), Pet.class);
        int createdPetId = Math.toIntExact(createdPet.getId());
        Assert.assertEquals(new Pet(createPetDTO),createdPet);


        // Get one pet test
        MvcResult getOneResult = mvc.perform(get("/pets/" + createdPetId)
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id", is(createdPetId)))
                        .andExpect(jsonPath("$.name", is(createdPet.getName())))
                        .andExpect(jsonPath("$.race", is(createdPet.getRace())))
                        .andReturn();

        System.out.println(getOneResult.getResponse().getContentAsString());
        Pet getOnePet = JsonMapperUtil.fromJsonStringToObject(getOneResult.getResponse().getContentAsString(), Pet.class);
        Assert.assertEquals(createdPet, getOnePet);


        // Get all pets test
        MvcResult getAllResult = mvc.perform(get("/pets")
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$[0].id", is(createdPetId)))
                        .andExpect(jsonPath("$[0].name", is(createdPet.getName())))
                        .andExpect(jsonPath("$[0].race", is(createdPet.getRace())))
                        .andReturn();

        System.out.println(getAllResult.getResponse().getContentAsString());


        // Update pet test
        Pet updatedPet = new Pet(createPetDTO);
        updatedPet.setName("marvin");
        updatedPet.setRace("german shepherd");
        String updatedPetJson = JsonMapperUtil.fromObjectToJsonString(updatedPet);

        mvc.perform(patch("/pets/" + createdPetId)
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPetJson))
                        .andExpect(status().isOk())
                        .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id", is(createdPetId)))
                        .andExpect(jsonPath("$.name", is(updatedPet.getName())))
                        .andExpect(jsonPath("$.race", is(updatedPet.getRace())));

        MvcResult getUpdatedResult = mvc.perform(get("/pets/" + createdPetId)
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id", is(createdPetId)))
                        .andExpect(jsonPath("$.name", is(updatedPet.getName())))
                        .andExpect(jsonPath("$.race", is(updatedPet.getRace())))
                        .andReturn();

        System.out.println(getUpdatedResult.getResponse().getContentAsString());
        Pet getUpdatedPet = JsonMapperUtil.fromJsonStringToObject(getUpdatedResult.getResponse().getContentAsString(), Pet.class);
        Assert.assertEquals(updatedPet.getName(), getUpdatedPet.getName());
        Assert.assertEquals(updatedPet.getRace(), getUpdatedPet.getRace());


        // Delete pet test
        mvc.perform(delete("/pets/" + createdPetId)
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        MvcResult getEmptyResult = mvc.perform(get("/pets")
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        Assert.assertEquals("[]", getEmptyResult.getResponse().getContentAsString());
        Assert.assertTrue(this.petRepository.findAll().isEmpty());
    }

    public static CreatePetDTO getTestPetDTO(Long userId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new CreatePetDTO("simas", "www", LocalDate.parse("26/07/2020", formatter),
                Character.valueOf('m'), Float.valueOf(2), "dog",
                "golden retriever", "unknown", userId);
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

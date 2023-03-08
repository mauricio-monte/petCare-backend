package com.petcare.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petcare.backend.PetCareBackendApplication;
import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.User;
import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.dto.dose.CreateDoseFromVaccineDTO;
import com.petcare.backend.dto.pet.CreatePetDTO;
import com.petcare.backend.dto.user.CreateUserDTO;
import com.petcare.backend.dto.user.LoginDTO;
import com.petcare.backend.dto.user.LoginReturnDTO;
import com.petcare.backend.dto.vaccine.CreateVaccineDTO;
import com.petcare.backend.repository.PetRepository;
import com.petcare.backend.repository.UserRepository;
import com.petcare.backend.repository.VaccineRepository;
import com.petcare.backend.service.PetService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PetCareBackendApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class VaccineControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private PetRepository petRepository;

    @Test
    public void vaccineControllerIntegrationTests()
            throws Exception {

        Pair<Long, String> utilPair = createUserLoginAndGetToken();
        Long userId = utilPair.getFirst();
        String bearerAuth = utilPair.getSecond();

        Pet petTest = createPetAndGetId(userId, bearerAuth);
        Long petId = petTest.getId();

        // Create vaccine with no doses test
        CreateVaccineDTO createVaccineDTO = getTestVaccineWithNoDosesDTO(petId);
        String createVaccineJson = JsonMapperUtil.fromObjectToJsonString(createVaccineDTO);

        MvcResult createVaccineResult = mvc.perform(post("/vaccines")
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createVaccineJson))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.description", is(createVaccineDTO.getDescription())))
                        .andExpect(jsonPath("$.veterinaryClinic", is(createVaccineDTO.getVeterinaryClinic())))
                        .andReturn();

        Vaccine createdVaccine = JsonMapperUtil.fromJsonStringToObject(createVaccineResult.getResponse().getContentAsString(), Vaccine.class);
        int createdVaccineId = Math.toIntExact(createdVaccine.getId());
        Assert.assertEquals(createVaccineDTO.getDescription(),createdVaccine.getDescription());


        // Get one pet test
        MvcResult getOneResult = mvc.perform(get("/vaccines/" + createdVaccineId)
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andReturn();

        System.out.println(getOneResult.getResponse().getContentAsString());
        Vaccine getOneVaccine = JsonMapperUtil.fromJsonStringToObject(getOneResult.getResponse().getContentAsString(), Vaccine.class);
        Assert.assertEquals(createdVaccine, getOneVaccine);


        // Get all pets test
        MvcResult getAllResult = mvc.perform(get("/vaccines")
                        .header("authorization", bearerAuth)
                        .param("petId", String.valueOf(petId))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$[0].id", is(createdVaccineId)))
                        .andExpect(jsonPath("$[0].description", is(createdVaccine.getDescription())))
                        .andReturn();


        // Update pet test
        Vaccine updatedVaccine = new Vaccine(createVaccineDTO);
        updatedVaccine.setDescription("scabies");
        String updatedVaccineJson = JsonMapperUtil.fromObjectToJsonString(updatedVaccine);

        mvc.perform(patch("/vaccines/" + createdVaccineId)
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedVaccineJson))
                        .andExpect(status().isOk())
                        .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id", is(createdVaccineId)))
                .andExpect(jsonPath("$.description", is(updatedVaccine.getDescription())));

        MvcResult getUpdatedResult = mvc.perform(get("/vaccines/" + createdVaccineId)
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id", is(createdVaccineId)))
                        .andExpect(jsonPath("$.description", is(updatedVaccine.getDescription())))
                        .andReturn();

        Vaccine getUpdatedVaccine = JsonMapperUtil.fromJsonStringToObject(getUpdatedResult.getResponse().getContentAsString(), Vaccine.class);
        Assert.assertEquals(updatedVaccine.getDescription(), getUpdatedVaccine.getDescription());



        // Delete pet test
        mvc.perform(delete("/vaccines/" + createdVaccineId)
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        Assert.assertTrue(this.vaccineRepository.findAll().isEmpty());
    }

    public static CreatePetDTO getTestPetDTO(Long userId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new CreatePetDTO("simas", "www", LocalDate.parse("26/07/2020", formatter),
                Character.valueOf('m'), Float.valueOf(2), "dog",
                "golden retriever", "unknown", userId);
    }

    public static CreateVaccineDTO getTestVaccineWithNoDosesDTO(Long petId) {
        return new CreateVaccineDTO("rabies", "healdog", petId, null);
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

    private Pet createPetAndGetId(Long userId, String bearerAuth) throws Exception {
        CreatePetDTO createPetDTO = PetControllerIT.getTestPetDTO(userId);
        return this.petRepository.save(new Pet(createPetDTO));
    }
}

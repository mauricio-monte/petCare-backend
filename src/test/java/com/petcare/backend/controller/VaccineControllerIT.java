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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private PetService petService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void petControllerIntegrationTests()
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

        System.out.println(createVaccineResult.getResponse().getContentAsString());

        /////////////
        // Create vaccine with doses test
//        CreateVaccineDTO createVaccineDTO2 = getTestVaccineWithDosesDTO(petId);
//        String createVaccineJson2 = JsonMapperUtil.fromObjectToJsonString(createVaccineDTO2);
//
//        MvcResult createVaccineResult2 = mvc.perform(post("/vaccines")
//                        .header("authorization", bearerAuth)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(createVaccineJson2))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.description", is(createVaccineDTO2.getDescription())))
//                .andExpect(jsonPath("$.veterinaryClinic", is(createVaccineDTO2.getVeterinaryClinic())))
//                .andReturn();
//
//        System.out.println(createVaccineResult2.getResponse().getContentAsString());
        //////


        Vaccine createdVaccine = JsonMapperUtil.fromJsonStringToObject(createVaccineResult.getResponse().getContentAsString(), Vaccine.class);
        int createdVaccineId = Math.toIntExact(createdVaccine.getId());
        Assert.assertEquals(createVaccineDTO.getDescription(),createdVaccine.getDescription());


//        // Get one pet test
//        MvcResult getOneResult = mvc.perform(get("/vaccine/" + createdVaccineId)
//                        .header("authorization", bearerAuth)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(createdVaccineId)))
//                .andExpect(jsonPath("$.description", is(createdVaccine.getDescription())))
//                .andReturn();
//
//        System.out.println(getOneResult.getResponse().getContentAsString());
//        Vaccine getOneVaccine = JsonMapperUtil.fromJsonStringToObject(getOneResult.getResponse().getContentAsString(), Vaccine.class);
//        Assert.assertEquals(createdVaccine, getOneVaccine);
//
//
        // Get all pets test
        MvcResult getAllResult = mvc.perform(get("/vaccine/" + petId)
                        .header("authorization", bearerAuth)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id", is(createdPetId)))
//                .andExpect(jsonPath("$[0].name", is(createdPet.getName())))
//                .andExpect(jsonPath("$[0].race", is(createdPet.getRace())))
                .andReturn();

        System.out.println(getAllResult.getResponse().getContentAsString());
//
//
//        // Update pet test
//        Pet updatedPet = new Pet(createPetDTO);
//        updatedPet.setName("marvin");
//        updatedPet.setRace("german shepherd");
//        String updatedPetJson = JsonMapperUtil.fromObjectToJsonString(updatedPet);
//
//        mvc.perform(patch("/pets/" + createdPetId)
//                        .header("authorization", bearerAuth)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(updatedPetJson))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(createdPetId)))
//                .andExpect(jsonPath("$.name", is(updatedPet.getName())))
//                .andExpect(jsonPath("$.race", is(updatedPet.getRace())));
//
//        MvcResult getUpdatedResult = mvc.perform(get("/pets/" + createdPetId)
//                        .header("authorization", bearerAuth)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(createdPetId)))
//                .andExpect(jsonPath("$.name", is(updatedPet.getName())))
//                .andExpect(jsonPath("$.race", is(updatedPet.getRace())))
//                .andReturn();
//
//        System.out.println(getUpdatedResult.getResponse().getContentAsString());
//        Pet getUpdatedPet = JsonMapperUtil.fromJsonStringToObject(getUpdatedResult.getResponse().getContentAsString(), Pet.class);
//        Assert.assertEquals(updatedPet.getName(), getUpdatedPet.getName());
//        Assert.assertEquals(updatedPet.getRace(), getUpdatedPet.getRace());
//
//
//        // Delete pet test
//        mvc.perform(delete("/pets/" + createdPetId)
//                        .header("authorization", bearerAuth)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        MvcResult getEmptyResult = mvc.perform(get("/pets")
//                        .header("authorization", bearerAuth)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//
//        Assert.assertEquals("[]", getEmptyResult.getResponse().getContentAsString());
    }

    public static CreatePetDTO getTestPetDTO(Long userId) {
        return new CreatePetDTO("simas", "www", new Date("26/07/2020"),
                Character.valueOf('m'), Float.valueOf(2), "dog",
                "golden retriever", "unknown", userId);
    }

    public static CreateVaccineDTO getTestVaccineWithNoDosesDTO(Long petId) {
        return new CreateVaccineDTO("rabies", "healdog", true, petId, null);
    }

    public static CreateVaccineDTO getTestVaccineWithDosesDTO(Long petId) {
        CreateDoseFromVaccineDTO dose1 = new CreateDoseFromVaccineDTO(new Date("26/07/2020"), true);
        CreateDoseFromVaccineDTO dose2 = new CreateDoseFromVaccineDTO(new Date("15/02/2024"), false);
        List<CreateDoseFromVaccineDTO> doses = new ArrayList<>();
        doses.add(dose1);
        doses.add(dose2);
        return new CreateVaccineDTO("cancer", "dogger", false, petId, doses);
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
//        String createPetJson = JsonMapperUtil.fromObjectToJsonString(createPetDTO);
//
//        MvcResult createPetResult = mvc.perform(post("/pets")
//                        .header("authorization", bearerAuth)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(createPetJson))
//                        .andReturn();
//
//        return JsonMapperUtil.fromJsonStringToObject(createPetResult.getResponse().getContentAsString(), Pet.class);
    }
}

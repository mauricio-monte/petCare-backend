package com.petcare.backend.dto;

import com.petcare.backend.dto.dose.CreateDoseDTO;
import com.petcare.backend.dto.dose.CreateDoseFromVaccineDTO;
import com.petcare.backend.dto.pet.CreatePetDTO;
import com.petcare.backend.dto.pet.UpdatePetDTO;
import com.petcare.backend.dto.user.CreateUserDTO;
import com.petcare.backend.dto.user.LoginDTO;
import com.petcare.backend.dto.user.UpdateUserDTO;
import com.petcare.backend.dto.vaccine.CreateVaccineDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static com.petcare.backend.util.Validator.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationTests {

    @Test
    public void createUserDTO() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> new CreateUserDTO(null, null, null),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(NOT_EMPTY_RESTRICTION, thrown.getMessage());

        Exception thrown2 = assertThrows(
                Exception.class,
                () -> new CreateUserDTO("", "teste@teste", ""),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(NOT_EMPTY_RESTRICTION, thrown2.getMessage());

        Exception thrown3 = assertThrows(
                Exception.class,
                () -> new CreateUserDTO("mateus", "teste", "asdasda"),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(INVALID_EMAIL, thrown3.getMessage());
    }
    @Test
    public void loginDTO() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> new LoginDTO(null, null),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(NOT_EMPTY_RESTRICTION, thrown.getMessage());

        Exception thrown2 = assertThrows(
                Exception.class,
                () -> new LoginDTO("", "teste@teste"),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(NOT_EMPTY_RESTRICTION, thrown2.getMessage());

        Exception thrown3 = assertThrows(
                Exception.class,
                () -> new LoginDTO("mateus", "teste"),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(INVALID_EMAIL, thrown3.getMessage());
    }

    @Test
    public void updateUserDTO() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> new UpdateUserDTO("mateus", "teste"),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(INVALID_EMAIL, thrown.getMessage());
    }

    @Test
    public void createPetDTO() {
        // Empty name
        Exception thrown = assertThrows(
                Exception.class,
                () -> new CreatePetDTO("", "www", new Date("26/07/2020"),
                        Character.valueOf('m'), Float.valueOf(2), "dog",
                        "golden retriever", "unknown", Long.valueOf(1)),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(NOT_EMPTY_RESTRICTION, thrown.getMessage());

        // Null character
        Exception thrown2 = assertThrows(
                Exception.class,
                () -> new CreatePetDTO("Simas", "www", new Date("26/07/2020"),
                        null, Float.valueOf(2), "dog",
                        "golden retriever", "unknown", Long.valueOf(1)),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(NOT_NULL_RESTRICTION, thrown2.getMessage());

        // Invalid character
        Exception thrown3 = assertThrows(
                Exception.class,
                () -> new CreatePetDTO("Simas", "www", new Date("26/07/2020"),
                        Character.valueOf('g'), Float.valueOf(2), "dog",
                        "golden retriever", "unknown", Long.valueOf(1)),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(INVALID_SEX, thrown3.getMessage());

        // Invalid date
        Exception thrown4 = assertThrows(
                Exception.class,
                () -> new CreatePetDTO("Simas", "www", new Date("26/07/2025"),
                        Character.valueOf('m'), Float.valueOf(2), "dog",
                        "golden retriever", "unknown", Long.valueOf(1)),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(INVALID_DATE, thrown4.getMessage());

        // Invalid weight
        Exception thrown5 = assertThrows(
                Exception.class,
                () -> new CreatePetDTO("Simas", "www", new Date("26/07/2020"),
                        Character.valueOf('m'), Float.valueOf(-1), "dog",
                        "golden retriever", "unknown", Long.valueOf(1)),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(INVALID_WEIGHT, thrown5.getMessage());

        // Null userId
        Exception thrownFinal = assertThrows(
                Exception.class,
                () -> new CreatePetDTO("Simas", "www", new Date("26/07/2020"),
                        Character.valueOf('m'), Float.valueOf(2), "dog",
                        "golden retriever", "unknown", null),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(NOT_NULL_RESTRICTION, thrownFinal.getMessage());
    }

    @Test
    public void updatePetDTO() {
        // Invalid character
        Exception thrown = assertThrows(
                Exception.class,
                () -> new UpdatePetDTO("Simas", "www", new Date("26/07/2020"),
                        Character.valueOf('g'), Float.valueOf(2), "dog",
                        "golden retriever", "unknown"),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(INVALID_SEX, thrown.getMessage());

        // Invalid date
        Exception thrown2 = assertThrows(
                Exception.class,
                () -> new UpdatePetDTO("Simas", "www", new Date("26/07/2025"),
                        Character.valueOf('m'), Float.valueOf(2), "dog",
                        "golden retriever", "unknown"),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(INVALID_DATE, thrown2.getMessage());

        // Invalid weight
        Exception thrown3 = assertThrows(
                Exception.class,
                () -> new UpdatePetDTO("Simas", "www", new Date("26/07/2020"),
                        Character.valueOf('m'), Float.valueOf(-1), "dog",
                        "golden retriever", "unknown"),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(INVALID_WEIGHT, thrown3.getMessage());
    }

    @Test
    public void createVaccineDTO() {
        // Empty description
        Exception thrown = assertThrows(
                Exception.class,
                () -> new CreateVaccineDTO("", "healdog", true, Long.valueOf(1), null),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(NOT_EMPTY_RESTRICTION, thrown.getMessage());

        // Null description
        Exception thrown2 = assertThrows(
                Exception.class,
                () -> new CreateVaccineDTO(null, "healdog", true, Long.valueOf(1), null),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(NOT_EMPTY_RESTRICTION, thrown2.getMessage());

        // Null petId
        Exception thrown3 = assertThrows(
                Exception.class,
                () -> new CreateVaccineDTO("rabies", "healdog", true, null, null),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(NOT_NULL_RESTRICTION, thrown3.getMessage());
    }

    @Test
    public void createDoseDTO() {
        // Null date
        Exception thrown = assertThrows(
                Exception.class,
                () -> new CreateDoseDTO(null, true, Long.valueOf(1)),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(NOT_NULL_RESTRICTION, thrown.getMessage());

        // Null description
        Exception thrown2 = assertThrows(
                Exception.class,
                () -> new CreateVaccineDTO(null, "healdog", true, Long.valueOf(1), null),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(NOT_EMPTY_RESTRICTION, thrown2.getMessage());

        // Null petId
        Exception thrown3 = assertThrows(
                Exception.class,
                () -> new CreateVaccineDTO("rabies", "healdog", true, null, null),
                "Expected constructor to throw, but it didn't"
        );
        Assert.assertEquals(NOT_NULL_RESTRICTION, thrown3.getMessage());
    }
}

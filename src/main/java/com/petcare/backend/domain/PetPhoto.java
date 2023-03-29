package com.petcare.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.petcare.backend.dto.photo.PetPhotoDTO;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

public class PetPhoto {

    @Id
    @SequenceGenerator(name = "photo_sequence", sequenceName = "photo_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_sequence")
    @Column(name = "id", updatable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotEmpty
    @Column(name = "photo", columnDefinition = "TEXT", nullable = false)
    private String photo;

    @Column(name = "description")
    private String description;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    public PetPhoto(String photo, String description) {
        this.photo = photo;
        this.description = description;
    }

    public PetPhoto(PetPhotoDTO petPhotoDTO) {
        this(petPhotoDTO.getPhoto(), petPhotoDTO.getDescription());
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}

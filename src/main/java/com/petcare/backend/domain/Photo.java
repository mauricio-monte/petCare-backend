package com.petcare.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.petcare.backend.dto.photo.PhotoDTO;
import com.petcare.backend.dto.photo.UpdatePhotoDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "photos")
@NoArgsConstructor
@AllArgsConstructor
public class Photo {

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

    public Photo(String photo, String description) {
        this.photo = photo;
        this.description = description;
    }

    public Photo(PhotoDTO photoDTO) {
        this(photoDTO.getPhoto(), photoDTO.getDescription());
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void updatePhoto(UpdatePhotoDTO updatePhotoDTO) {
        this.description = updatePhotoDTO.getDescription();
    }
}

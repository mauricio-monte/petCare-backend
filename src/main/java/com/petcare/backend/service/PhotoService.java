package com.petcare.backend.service;

import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.Photo;
import com.petcare.backend.dto.photo.PhotoDTO;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PhotoService {

    private PhotoRepository photoRepository;
    private PetService petService;

    public Photo addPhoto(PhotoDTO photoDTO) throws PetNotFoundException {
        Pet pet = this.petService.getPetById(photoDTO.getPetId());
        Photo photo = new Photo(photoDTO);
        photo.setPet(pet);
        this.photoRepository.save(photo);

        return photo;
    }

    public List<Photo> getAllPhotosByPet(Long petId) throws PetNotFoundException {
        Pet pet = this.petService.getPetById(petId);
        return this.photoRepository.findAllByPet(pet);
    }

    public List<Photo> getAllPhotos() {
        return this.photoRepository.findAll();
    }
}

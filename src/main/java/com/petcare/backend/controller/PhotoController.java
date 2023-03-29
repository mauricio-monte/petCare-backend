package com.petcare.backend.controller;

import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.Photo;
import com.petcare.backend.dto.photo.PhotoDTO;
import com.petcare.backend.dto.photo.UpdatePhotoDTO;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.exception.PhotoNotFoundException;
import com.petcare.backend.service.PhotoService;
import com.petcare.backend.util.UrlConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = UrlConstants.PHOTO_URL)
@AllArgsConstructor
public class PhotoController {

    private PhotoService photoService;

    @GetMapping
    public ResponseEntity<List<Photo>> getPhotosByPet(@RequestParam(required = false) Long petId) {
        try {
            return new ResponseEntity<>(this.photoService.getAllPhotosByPet(petId), HttpStatus.OK);
        } catch (PetNotFoundException petNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, petNotFoundException.getMessage(), petNotFoundException);
        }
    }

    @PostMapping
    public ResponseEntity<Photo> addPetPhoto(@Valid @RequestBody PhotoDTO photoDTO) {
        try {
            return new ResponseEntity<>(photoService.addPhoto(photoDTO), HttpStatus.CREATED);
        } catch (PetNotFoundException petNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, petNotFoundException.getMessage(), petNotFoundException);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Photo> updatePhoto(@NotNull @PathVariable("id") Long photoId, @Valid @RequestBody UpdatePhotoDTO updatePhotoDTO) {
        try {
            return new ResponseEntity<>(photoService.updatePhoto(photoId, updatePhotoDTO), HttpStatus.OK);
        } catch (PhotoNotFoundException photoNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, photoNotFoundException.getMessage(), photoNotFoundException);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pet> deletePhoto(@NotNull @PathVariable("id") Long photoId) {
        try {
            photoService.deletePhoto(photoId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PhotoNotFoundException photoNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, photoNotFoundException.getMessage(), photoNotFoundException);
        }
    }
}

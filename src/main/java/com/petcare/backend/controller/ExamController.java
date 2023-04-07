package com.petcare.backend.controller;

import com.petcare.backend.domain.Exam;
import com.petcare.backend.dto.exam.ExamDTO;
import com.petcare.backend.exception.ExamNotFoundException;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.service.ExamService;
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
@RequestMapping(path = UrlConstants.EXAM_URL)
@AllArgsConstructor
public class ExamController {

    private ExamService examService;

    @PostMapping
    public ResponseEntity<Exam> addExam(@Valid @RequestBody ExamDTO examDTO) {
        try {
            return new ResponseEntity<>(examService.addExam(examDTO), HttpStatus.CREATED);
        } catch (PetNotFoundException petNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, petNotFoundException.getMessage(), petNotFoundException);
        }
    }

    @GetMapping
    public ResponseEntity<List<Exam>> getExamsByPet(@RequestParam Long petId) {
        try {
            return new ResponseEntity<>(this.examService.getAllExamsByPet(petId), HttpStatus.OK);
        } catch (PetNotFoundException petNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, petNotFoundException.getMessage(), petNotFoundException);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExamsById(@NotNull @PathVariable("id") Long examId) {
        try {
            return new ResponseEntity<>(this.examService.getExamById(examId), HttpStatus.OK);
        } catch (ExamNotFoundException examNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, examNotFoundException.getMessage(), examNotFoundException);
        }
    }
}

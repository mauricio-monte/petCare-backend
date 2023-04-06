package com.petcare.backend.controller;

import com.petcare.backend.domain.Exam;
import com.petcare.backend.domain.ExamFile;
import com.petcare.backend.domain.Photo;
import com.petcare.backend.dto.exam.ExamDTO;
import com.petcare.backend.dto.photo.PhotoDTO;
import com.petcare.backend.exception.ExamNotFoundException;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.service.ExamService;
import com.petcare.backend.util.UrlConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

import static com.petcare.backend.util.StatusConstants.INTERNAL_IO_ERROR_STATUS;

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

    @PostMapping("/file")
    public ResponseEntity<ExamFile> addExamFile(@NotNull @RequestParam Long examId,
                                                @RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(examService.addExamFile(examId, file), HttpStatus.CREATED);
        } catch (ExamNotFoundException examNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, examNotFoundException.getMessage(), examNotFoundException);
        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_IO_ERROR_STATUS, ioException);
        }
    }


}

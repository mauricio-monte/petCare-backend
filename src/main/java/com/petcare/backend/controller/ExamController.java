package com.petcare.backend.controller;

import com.petcare.backend.domain.Exam;
import com.petcare.backend.domain.ExamFile;
import com.petcare.backend.domain.Photo;
import com.petcare.backend.dto.exam.ExamDTO;
import com.petcare.backend.dto.exam.ExamFileResponseDTO;
import com.petcare.backend.exception.ExamFileNotFoundException;
import com.petcare.backend.exception.ExamNotFoundException;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.service.ExamService;
import com.petcare.backend.util.UrlConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/exam_file")
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

    @GetMapping("/exam_file/{id}")
    public ResponseEntity<byte[]> getExamFileById(@NotNull @PathVariable("id") Long examFileId) {
        try {
            ExamFile examFile = this.examService.getExamFileById(examFileId);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + examFile.getFileName() + "\"")
                    .body(examFile.getData());

        } catch (ExamFileNotFoundException examFileNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, examFileNotFoundException.getMessage(), examFileNotFoundException);
        }
    }



//    @GetMapping
//    public ResponseEntity<List<ExamFile>> getFilesByExam(@RequestParam Long examId) {
//        try {
//            List<ExamFileResponseDTO> responseFiles = this.examService.getAllFilesByExam(examId).map(examFile -> {
//                String fileDownloadUri = ServletUriComponentsBuilder
//                        .fromCurrentContextPath()
//                        .path("/files/")
//                        .path(examFile.getId())
//
//            }).collect(Collectors.toList());
//            return new ResponseEntity<>(this.examService.getAllFilesByExam(), HttpStatus.OK);
//        } catch (PetNotFoundException petNotFoundException) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, petNotFoundException.getMessage(), petNotFoundException);
//        } catch (ExamNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

}

package com.petcare.backend.controller;

import com.petcare.backend.domain.Exam;
import com.petcare.backend.domain.ExamFile;
import com.petcare.backend.dto.exam.ExamDTO;
import com.petcare.backend.dto.exam.examFile.ExamFileViewDTO;
import com.petcare.backend.exception.ExamFileNotFoundException;
import com.petcare.backend.exception.ExamNotFoundException;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.service.ExamService;
import com.petcare.backend.util.UrlConstants;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<ExamFileViewDTO> addExamFile(@NotNull @RequestParam Long examId,
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

    @GetMapping("/exam_files")
    public ResponseEntity<List<ExamFileViewDTO>> getExamFiles(@RequestParam Long examId) {
        try {
            return new ResponseEntity<>(this.examService.getAllFilesByExam(examId), HttpStatus.OK);
        } catch (ExamNotFoundException examNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, examNotFoundException.getMessage(), examNotFoundException);
        }
    }

    @GetMapping("/exam_file/{id}")
    public ResponseEntity<Resource> getExamFileById(@NotNull @PathVariable("id") Long examFileId) {
        try {
            ExamFile examFile = this.examService.getExamFileById(examFileId);
            ByteArrayResource resource = new ByteArrayResource(examFile.getData());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(resource.contentLength())
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            ContentDisposition.attachment()
                                    .filename(examFile.getFileName())
                                    .build().toString())
                    .body(resource);


        } catch (ExamFileNotFoundException examFileNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, examFileNotFoundException.getMessage(), examFileNotFoundException);
        }
    }
}

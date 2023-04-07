package com.petcare.backend.controller;

import com.petcare.backend.domain.ExamFile;
import com.petcare.backend.domain.ExamImage;
import com.petcare.backend.dto.exam.examFile.ExamFileViewDTO;
import com.petcare.backend.dto.exam.examFile.ExamImageDTO;
import com.petcare.backend.exception.ExamFileNotFoundException;
import com.petcare.backend.exception.ExamImageNotFoundException;
import com.petcare.backend.exception.ExamNotFoundException;
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
@RequestMapping(path = UrlConstants.EXAM_FILE_URL)
@AllArgsConstructor
public class ExamFilesController {

    private ExamService examService;

    @PostMapping("/file")
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

    @GetMapping("/files")
    public ResponseEntity<List<ExamFileViewDTO>> getExamFiles(@NotNull @RequestParam Long examId) {
        try {
            return new ResponseEntity<>(this.examService.getAllFilesByExam(examId), HttpStatus.OK);
        } catch (ExamNotFoundException examNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, examNotFoundException.getMessage(), examNotFoundException);
        }
    }

    @GetMapping("/file/{id}")
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


    @PostMapping("/image")
    public ResponseEntity<ExamImage> addExamImage(@Valid @RequestBody ExamImageDTO examImageDTO) {
        try {
            return new ResponseEntity<>(examService.addExamImage(examImageDTO), HttpStatus.CREATED);
        } catch (ExamNotFoundException examNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, examNotFoundException.getMessage(), examNotFoundException);
        }
    }

    @GetMapping("/images")
    public ResponseEntity<List<ExamImage>> getExamImages(@RequestParam Long examId) {
        try {
            return new ResponseEntity<>(this.examService.getAllImagesByExam(examId), HttpStatus.OK);
        } catch (ExamNotFoundException examNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, examNotFoundException.getMessage(), examNotFoundException);
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<ExamImage> getExamImageById(@NotNull @PathVariable("id") Long examImageId) {
        try {
            return new ResponseEntity<>(this.examService.getExamImageById(examImageId), HttpStatus.OK);
        } catch (ExamImageNotFoundException examImageNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, examImageNotFoundException.getMessage(), examImageNotFoundException);
        }
    }
}

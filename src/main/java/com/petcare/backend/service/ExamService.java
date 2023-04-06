package com.petcare.backend.service;

import com.petcare.backend.domain.Exam;
import com.petcare.backend.domain.ExamFile;
import com.petcare.backend.domain.Pet;
import com.petcare.backend.dto.exam.ExamDTO;
import com.petcare.backend.exception.ExamFileNotFoundException;
import com.petcare.backend.exception.ExamNotFoundException;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.repository.ExamFileRepository;
import com.petcare.backend.repository.ExamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExamService {

    private PetService petService;
    private ExamRepository examRepository;
    private ExamFileRepository examFileRepository;

    public Exam addExam(ExamDTO examDTO) throws PetNotFoundException {
        Pet pet = this.petService.getPetById(examDTO.getPetId());
        Exam exam = new Exam(examDTO);
        exam.setPet(pet);

        return this.examRepository.save(exam);
    }

    public Exam getExamById(Long examId) throws ExamNotFoundException {
        Optional<Exam> optionalExam = this.examRepository.findById(examId);

        if (optionalExam.isPresent()) {
            return optionalExam.get();
        } else {
            throw new ExamNotFoundException();
        }
    }

    public ExamFile addExamFile(Long examId, MultipartFile multipartFile) throws IOException, ExamNotFoundException {
        Exam exam = this.getExamById(examId);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        ExamFile examFile = new ExamFile(fileName, multipartFile.getBytes());
        examFile.setExam(exam);

        return examFileRepository.save(examFile);
    }

    public ExamFile getExamFileById(Long examFileId) throws ExamFileNotFoundException {
        Optional<ExamFile> examFileOptional = examFileRepository.findById(examFileId);

        if (examFileOptional.isPresent()) {
            return examFileOptional.get();
        } else {
            throw new ExamFileNotFoundException();
        }
    }
}

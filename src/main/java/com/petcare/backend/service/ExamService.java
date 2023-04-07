package com.petcare.backend.service;

import com.petcare.backend.domain.Exam;
import com.petcare.backend.domain.ExamFile;
import com.petcare.backend.domain.Pet;
import com.petcare.backend.dto.exam.ExamDTO;
import com.petcare.backend.dto.exam.examFile.ExamFileViewDTO;
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
import java.util.ArrayList;
import java.util.List;
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

    public ExamFileViewDTO addExamFile(Long examId, MultipartFile multipartFile) throws IOException, ExamNotFoundException {
        Exam exam = this.getExamById(examId);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        ExamFile examFile = new ExamFile(fileName, multipartFile.getContentType(), multipartFile.getBytes());
        examFile.setExam(exam);

        return new ExamFileViewDTO(examFileRepository.save(examFile));
    }

    public Exam getExamById(Long examId) throws ExamNotFoundException {
        Optional<Exam> optionalExam = this.examRepository.findById(examId);

        if (optionalExam.isPresent()) {
            return optionalExam.get();
        } else {
            throw new ExamNotFoundException();
        }
    }

    public ExamFile getExamFileById(Long examFileId) throws ExamFileNotFoundException {
        Optional<ExamFile> examFileOptional = examFileRepository.findById(examFileId);

        if (examFileOptional.isPresent()) {
            return examFileOptional.get();
        } else {
            throw new ExamFileNotFoundException();
        }
    }

    public List<Exam> getAllExamsByPet(Long petId) throws PetNotFoundException {
        Pet pet = this.petService.getPetById(petId);
        return this.examRepository.findAllByPet(pet);
    }

    public List<ExamFileViewDTO> getAllFilesByExam(Long examID) throws ExamNotFoundException {
        Exam exam = this.getExamById(examID);
        List<ExamFileViewDTO> examFileViews = new ArrayList<>();

        this.examFileRepository.findAllByExam(exam).forEach(examFile ->
                examFileViews.add(new ExamFileViewDTO()));

        return examFileViews;
    }
}

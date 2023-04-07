package com.petcare.backend.service;

import com.petcare.backend.domain.Exam;
import com.petcare.backend.domain.ExamFile;
import com.petcare.backend.domain.ExamImage;
import com.petcare.backend.domain.Pet;
import com.petcare.backend.dto.exam.ExamDTO;
import com.petcare.backend.dto.exam.examFile.ExamFileViewDTO;
import com.petcare.backend.dto.exam.examFile.ExamImageDTO;
import com.petcare.backend.exception.ExamFileNotFoundException;
import com.petcare.backend.exception.ExamImageNotFoundException;
import com.petcare.backend.exception.ExamNotFoundException;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.repository.ExamFileRepository;
import com.petcare.backend.repository.ExamImageRepository;
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
    private ExamImageRepository examImageRepository;

    // Exams operations
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

    public List<Exam> getAllExamsByPet(Long petId) throws PetNotFoundException {
        Pet pet = this.petService.getPetById(petId);
        return this.examRepository.findAllByPet(pet);
    }

    // ExamFiles operations
    public ExamFileViewDTO addExamFile(Long examId, MultipartFile multipartFile) throws IOException, ExamNotFoundException {
        Exam exam = this.getExamById(examId);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        ExamFile examFile = new ExamFile(fileName, multipartFile.getContentType(), multipartFile.getBytes());
        examFile.setExam(exam);

        return new ExamFileViewDTO(examFileRepository.save(examFile));
    }

    public ExamFile getExamFileById(Long examFileId) throws ExamFileNotFoundException {
        Optional<ExamFile> examFileOptional = examFileRepository.findById(examFileId);

        if (examFileOptional.isPresent()) {
            return examFileOptional.get();
        } else {
            throw new ExamFileNotFoundException();
        }
    }

    public List<ExamFileViewDTO> getAllFilesByExam(Long examID) throws ExamNotFoundException {
        Exam exam = this.getExamById(examID);
        List<ExamFileViewDTO> examFileViews = new ArrayList<>();

        this.examFileRepository.findAllByExam(exam).forEach(examFile -> examFileViews.add(new ExamFileViewDTO()));

        return examFileViews;
    }

    // ExamImages operations
    public ExamImage addExamImage(ExamImageDTO examImageDTO) throws ExamNotFoundException {
        Exam exam = this.getExamById(examImageDTO.getExamId());

        ExamImage examImage = new ExamImage(examImageDTO);
        examImage.setExam(exam);

        return examImageRepository.save(examImage);
    }

    public ExamImage getExamImageById(Long examImageId) throws ExamImageNotFoundException {
        Optional<ExamImage> examImageOptional = examImageRepository.findById(examImageId);

        if (examImageOptional.isPresent()) {
            return examImageOptional.get();
        } else {
            throw new ExamImageNotFoundException();
        }
    }

    public List<ExamImage> getAllImagesByExam(Long examID) throws ExamNotFoundException {
        Exam exam = this.getExamById(examID);
        return this.examImageRepository.findAllByExam(exam);
    }
}

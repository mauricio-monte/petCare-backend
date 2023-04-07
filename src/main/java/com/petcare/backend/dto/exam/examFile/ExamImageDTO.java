package com.petcare.backend.dto.exam.examFile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.petcare.backend.domain.Exam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamImageDTO {

    private String imageName;
    @NotEmpty
    private String image;
    @NotNull
    private Long examId;
}

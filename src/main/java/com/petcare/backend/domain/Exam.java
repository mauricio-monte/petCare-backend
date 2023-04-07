package com.petcare.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.petcare.backend.dto.exam.ExamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "exams")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Exam {

    @Id
    @SequenceGenerator(name = "exam_sequence", sequenceName = "exam_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exam_sequence")
    @Column(name = "id", updatable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    @NotNull
    @Column(name = "date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate date;

    @NotEmpty
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Exam(ExamDTO examDTO) {
        this.date = examDTO.getDate();
        this.title = examDTO.getTitle();
        this.description = examDTO.getDescription();
    }
}

package com.petcare.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.petcare.backend.dto.exam.examFile.ExamImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "exam_images")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamImage {

    @Id
    @SequenceGenerator(name = "exam_image_sequence", sequenceName = "exam_image_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exam_image_sequence")
    @Column(name = "id", updatable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "exam_image_name")
    private String imageName;

    @NotEmpty
    @Column(name = "exam_image", columnDefinition = "TEXT", nullable = false)
    private String image;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", referencedColumnName = "id")
    private Exam exam;

    public ExamImage(ExamImageDTO examImageDTO) {
        this.imageName = examImageDTO.getImageName();
        this.image = examImageDTO.getImage();
    }
}

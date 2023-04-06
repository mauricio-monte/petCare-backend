package com.petcare.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "exam_files")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamFile {

    @Id
    @SequenceGenerator(name = "exam_file_sequence", sequenceName = "exam_file_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exam_file_sequence")
    @Column(name = "id", updatable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "exam_file_name")
    private String fileName;

    @Column(name = "exam_file_type")
    private String fileType;

    @Lob
    @Column(name = "exam_file_data")
    private byte[] data;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", referencedColumnName = "id")
    private Exam exam;

    public ExamFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

}

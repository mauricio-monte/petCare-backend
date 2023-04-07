package com.petcare.backend.dto.exam.examFile;

import com.petcare.backend.domain.ExamFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamFileViewDTO {

    private Long id;
    private Long examId;
    private String fileName;
    private String type;

    public ExamFileViewDTO(ExamFile examFile) {
        this(examFile.getId(), examFile.getExamId(),
                examFile.getFileName(), examFile.getFileType());
    }
}

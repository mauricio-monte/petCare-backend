package com.petcare.backend.dto.exam.examFile;

import com.petcare.backend.domain.ExamFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamFileResponseDTO {
    private String fileName;
    private String url;
    private String type;

    public ExamFileResponseDTO(ExamFile examFile, String url) {
        this.fileName = examFile.getFileName();
        this.url = url;
        this.type = examFile.getFileType();
    }
}

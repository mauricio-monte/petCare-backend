package com.petcare.backend.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatusReturn {

    @SerializedName("status")
    private String status;

    @SerializedName("status_code")
    private int statusCode;
}

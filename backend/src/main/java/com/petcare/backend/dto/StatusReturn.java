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

    public void setStatus(String status, int statusCode) {
        this.setStatus(status);
        this.setStatusCode(statusCode);
    }
}

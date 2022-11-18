package com.petcare.backend.dto;


import com.google.gson.annotations.SerializedName;
import com.petcare.backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreationReturnDTO extends StatusReturn {
    @SerializedName("name")
    public String name;
    @SerializedName("username")
    public String username;
    @SerializedName("email")
    public String email;

    public UserCreationReturnDTO(User user) {
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public UserCreationReturnDTO(StatusReturn statusReturn) {
        super.setStatus(statusReturn.getStatus(), statusReturn.getStatusCode());
    }
}

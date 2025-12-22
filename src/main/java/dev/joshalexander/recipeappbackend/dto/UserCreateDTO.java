package dev.joshalexander.recipeappbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {
    private String todoistAccessToken;
    private String todoistUserId;
    @NotBlank
    private String name;
    private String email;
}

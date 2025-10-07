package dev.joshalexander.recipeappbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {
    private String todoistAccessToken;
    private String todoistUserId;
    private String name;
    private String email;
}

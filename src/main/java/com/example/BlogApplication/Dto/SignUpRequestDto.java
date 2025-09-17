package com.example.BlogApplication.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    @Schema(description = "Username", example = "dhoni007")
    private String username;
    @Schema(description = "User email", example = "dhoni@gmail.com")
    private String email;
    @Schema(description = "User password", example = "Dhoni007")
    private String password;

}

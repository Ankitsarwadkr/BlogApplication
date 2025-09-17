package com.example.BlogApplication.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequestDto {
    @Schema(description = "User email", example = "virat@gmail.com")
    String email;
    @Schema(description = "User password", example = "123445678")
    String password;
}

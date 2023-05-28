package com.rudykart.person.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotEmpty(message = "input is required")
    @NotNull(message = "input is required")
    private String name;
    @NotEmpty(message = "input is required")
    @NotNull(message = "input is required")
    private String email;
    @NotEmpty(message = "input is required")
    @NotNull(message = "input is required")
    private String password;
    @NotEmpty(message = "input is required")
    @NotNull(message = "input is required")
    private String role;
}

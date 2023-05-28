package com.rudykart.person.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PersonDto {
    private Long id;
    @NotEmpty(message = "Input can't empty")
    @NotNull(message = "Input can't empty")
    private String name;
    @NotNull(message = "Input can't empty")
    @Past
    private LocalDate birthDate;
    private int age;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

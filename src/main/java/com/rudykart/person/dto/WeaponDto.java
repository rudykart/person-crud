package com.rudykart.person.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WeaponDto {
    private Long id;
    
    @NotEmpty(message = "Weapon name cannot be empty")
    @NotNull(message = "Weapon name cannot be null")
    private String name;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

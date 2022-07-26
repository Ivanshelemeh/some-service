package com.example.someservice.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record PetDto(@Valid @NotBlank String sex, @NotNull LocalDateTime bornDay, @NotBlank String nickName , Long id) {
}

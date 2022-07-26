package com.example.someservice.dto;

import com.example.someservice.entity.Role;
import com.example.someservice.validator.constraint.UniqName;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public record AccountDtoForm(@NotBlank @UniqName String name, @NotBlank String password, Set<Role> roleSet) {
}

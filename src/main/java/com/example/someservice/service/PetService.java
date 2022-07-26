package com.example.someservice.service;

import com.example.someservice.dto.PetDto;
import com.example.someservice.entity.Pet;

import java.util.List;

public interface PetService {

    List<PetDto> findAllPets();

    Pet findPetByPetId(Long id);

    void deletePet(Long id);

    Pet createPet(PetDto dto);

    PetDto updateById(Long id);
}

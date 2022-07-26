package com.example.someservice.service.impl;

import com.example.someservice.converter.Converter;
import com.example.someservice.dto.PetDto;
import com.example.someservice.entity.Pet;
import com.example.someservice.repository.PetRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceImplTest {

    @Mock
    private PetRepository repository;
    @Mock
    private Converter converter;

    @InjectMocks
    private PetServiceImpl service;

    private Pet pet;
    private Pet pet2;

    @Test
    void should_get_all_Pets_from_repo() {
        pet = mock(Pet.class);
        pet2 = mock(Pet.class);
        repository.save(pet);
        repository.save(pet2);

        given(repository.findAll()).willReturn(List.of(pet, pet2));
        var list = service.findAllPets();
        verify(repository, times(1)).findAll();
        assertThat(list.size()).isNotNull();
        assertThat(list.get(0)).isInstanceOf(PetDto.class);

    }

    @Test
    void find_PetByPetId() {
        pet2 = mock(Pet.class);
        repository.save(pet2);

        lenient().when(repository.findById(anyLong())).thenReturn(Optional.of(pet2));
        var pet = service.findPetByPetId(pet2.getId());
        assertThat(pet2).isNotNull().isInstanceOf(Pet.class);
    }

    @Test
    void should_delete_pet_successfully() {
        pet = mock(Pet.class);
        repository.save(pet);

        repository.deleteById(pet.getId());
        verify(repository, times(1)).deleteById(anyLong());

        assertFalse(repository.existsById(pet.getId()));

    }

    @Test
    void should_create_a_new_pet_successfully() {
        PetDto dto = new PetDto("male", LocalDateTime.now().plusMinutes(60), "Frodo", 2L);
        var pet3 = service.createPet(dto);
        assertEquals(dto.nickName(), pet3.getNickName());


    }
}
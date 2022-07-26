package com.example.someservice.service.impl;

import com.example.someservice.converter.Converter;
import com.example.someservice.dto.PetDto;
import com.example.someservice.entity.Pet;
import com.example.someservice.exeption.PetNotFoundException;
import com.example.someservice.repository.PetRepository;
import com.example.someservice.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final Converter converter;

    @Override
    public List<PetDto> findAllPets() {
        return petRepository.findAll()
                .stream().map(Converter::fromPetEntity)
                .collect(Collectors.toList());

    }

    @Override
    public Pet findPetByPetId(Long id) {
        Optional<Pet> optionalPet = petRepository.findById(id).stream().findFirst();
        if (optionalPet.isEmpty()) {
            throw new PetNotFoundException("not pet with such id");
        }
        return optionalPet.get();


    }

    @Override
    public void deletePet(@NotNull Long id) {
        Optional<Pet> pet = petRepository.findById(id);
        if (!pet.isPresent()) {
            throw new PetNotFoundException("pet not exists with item " + id);
        }
        petRepository.deleteById(pet.get().getId());
    }

    @Override
    public Pet createPet(@Valid PetDto petDto) {
      //  PetDto dto = new PetDto(petDto.sex(), petDto.bornDay(), petDto.nickName(), petDto.id());
       // Pet pet = Converter.fromDto(petDto);
      // return petRepository.save(pet);
        return Optional.ofNullable(Converter.fromDto(petDto)).get();
    }

    @Override
    public PetDto updateById(Long id) {
        Optional<Pet> dtoOptional = Optional.ofNullable(petRepository.findById(id).stream().findAny().orElseThrow(()
        -> new PetNotFoundException("not such pet exists")));
        Pet pet = dtoOptional.get();
        pet.setId(pet.getId());
        pet.setSex(pet.getSex());
        pet.setNickName(pet.getNickName());
        pet.setBithDay(pet.getBithDay());
        PetDto dto = converter.convertFromEntity(pet);
        return dto;
    }

}

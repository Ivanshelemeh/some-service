package com.example.someservice.controllers;

import com.example.someservice.dto.PetDto;
import com.example.someservice.entity.Pet;
import com.example.someservice.exeption.PetNotFoundException;
import com.example.someservice.service.impl.PetServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/pet/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class PetController {

    private final PetServiceImpl petService;


    @GetMapping(value = "/pets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('AUTHORIZED')")
    public ResponseEntity<List<PetDto>> getPets() {
        return ResponseEntity.ok(petService.findAllPets());
    }

    @PostMapping(value = "/make", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('AUTHORIZED')")
    public void createPet(@Valid @RequestBody PetDto petDto) {
        if (petDto == null) {
            throw new PetNotFoundException("such Pet could not create" + petDto);

        }
        petService.createPet(petDto);
    }

    @ApiOperation("This method update Pet by item id")
    @PreAuthorize("hasRole('AUTHORIZED')")
    @PatchMapping(value = "/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<PetDto> updatePet(@PathVariable Long id){
       return ResponseEntity.ok(petService.updateById(id));

    }

    @ApiOperation("This method delete raw pet by item id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AUTHORIZED')")
    public ResponseEntity<?> removePet(@PathVariable Long  id){
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("This method finds raw of pet by item id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('AUTHORIZED')")
    public ResponseEntity<Pet> getPet(@PathVariable Long id){
        return ResponseEntity.ok(petService.findPetByPetId(id));
    }


}

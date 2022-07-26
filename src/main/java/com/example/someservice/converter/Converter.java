package com.example.someservice.converter;

import com.example.someservice.dto.AccountDtoForm;
import com.example.someservice.dto.PetDto;
import com.example.someservice.entity.Pet;
import com.example.someservice.entity.UserAccount;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public static final ModelMapper MAPPER = new ModelMapper();

    public static AccountDtoForm fromAccountEntity(UserAccount account) {
        AccountDtoForm dtoForm = new AccountDtoForm(account.getName(), account.getPassword(), account.getRoles());
        return dtoForm;
    }

    public static PetDto fromPetEntity(Pet pet) {
        return new PetDto(pet.getSex(), pet.getBithDay(), pet.getNickName(), pet.getId());
    }

    public PetDto convertFromEntity(Pet pet) {
        return MAPPER.map(pet, PetDto.class);
    }

    public static Pet fromDto(PetDto petDto) {
        MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Pet pet = MAPPER.map(petDto, Pet.class);
        pet.setId(petDto.id());
        pet.setNickName(petDto.nickName());
        pet.setBithDay(petDto.bornDay());
        pet.setSex(petDto.sex());
        return pet;

    }

    public static UserAccount fromDto(AccountDtoForm accountDtoForm) {
        UserAccount userAccount=MAPPER.map(accountDtoForm, UserAccount.class);
        userAccount.setRoles(accountDtoForm.roleSet());
        userAccount.setName(accountDtoForm.name());
        userAccount.setPassword(accountDtoForm.password());
        return userAccount;
    }


}

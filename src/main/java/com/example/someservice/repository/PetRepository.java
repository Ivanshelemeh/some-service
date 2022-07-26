package com.example.someservice.repository;

import com.example.someservice.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p from Pet  p")
    List<Pet> findAll();

    @Query("select p from Pet p where p.id =:id")
    Optional<Pet> findById(@Param("id") Long id);

    void deleteById( Long id);
}

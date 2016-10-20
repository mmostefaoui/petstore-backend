package com.rbcits.sdata.services;

import com.rbcits.sdata.domain.dtos.PetDto;
import com.rbcits.sdata.domain.entities.Category;
import com.rbcits.sdata.domain.entities.Pet;
import com.rbcits.sdata.exceptions.ResourceAlreadyExistException;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

public interface IPetService {

    Pet addPet(Pet pet) throws ResourceAlreadyExistException;

    Pet findPetById(Long petId);

    Collection<Pet> getAllPets(Specification<Pet> spec);

    Collection<Pet> getAllPetByCategory(Long category);

    void deletePet(Pet pet);

    Pet updatePet(Long petId, PetDto petDto);
}

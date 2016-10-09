package com.rbcits.sdata.services;

import com.rbcits.sdata.domain.entities.Pet;
import com.rbcits.sdata.exceptions.ResourceAlreadyExistException;

import java.util.Collection;
import java.util.List;

public interface IPetService {

    Pet addPet(Pet pet) throws ResourceAlreadyExistException;

    Pet findPetById(Long petId);

    Collection<Pet> getAllPets();

    void deletePet(Pet pet);
}

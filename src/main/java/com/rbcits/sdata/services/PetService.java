package com.rbcits.sdata.services;

import com.rbcits.sdata.domain.entities.Pet;
import com.rbcits.sdata.domain.repository.AppUserRepository;
import com.rbcits.sdata.domain.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class PetService implements IPetService {
    private final PetRepository petRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public PetService(PetRepository petRepository, AppUserRepository appUserRepository) {
        this.petRepository = petRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Pet addPet(Pet pet) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pet.setUser(appUserRepository.findByUsername(principal.getUsername()));
        // check category exists already


        return petRepository.save(pet);
    }

    @Override
    public Pet findPetById(Long petId) {
        return petRepository.findOne(petId);
    }

    @Override
    public Collection<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public void deletePet(Pet pet) {
        petRepository.delete(pet);
    }

}

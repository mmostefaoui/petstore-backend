package com.rbcits.sdata.services;

import com.rbcits.sdata.domain.dtos.CategoryDto;
import com.rbcits.sdata.domain.dtos.PetDto;
import com.rbcits.sdata.domain.entities.Category;
import com.rbcits.sdata.domain.entities.Pet;
import com.rbcits.sdata.domain.entities.Tag;
import com.rbcits.sdata.domain.repository.AppUserRepository;
import com.rbcits.sdata.domain.repository.PetRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PetService implements IPetService {
    private final PetRepository petRepository;
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PetService(PetRepository petRepository, AppUserRepository appUserRepository, ModelMapper modelMapper) {
        this.petRepository = petRepository;
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Pet addPet(Pet pet) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pet.setUser(appUserRepository.findByUsername(principal.getUsername()));
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

    @Override
    public Pet updatePet(Long petId, PetDto petDto) {
        Pet update = petRepository.findOne(petId);

        update.setCategory(modelMapper.map(petDto.getCategory(), Category.class));
        update.setName(petDto.getName());
        update.setPhotoUrls(petDto.getPhotoUrls());
        java.lang.reflect.Type targetListType = new TypeToken<Set<Tag>>() {}.getType();
        update.setTags(modelMapper.map(petDto.getTags(), targetListType));
        update.setStatus(petDto.getStatus());

        return petRepository.save(update);
    }

}

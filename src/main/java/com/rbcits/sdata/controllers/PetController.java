package com.rbcits.sdata.controllers;


import com.rbcits.sdata.domain.entities.Pet;
import com.rbcits.sdata.domain.repository.PetRepository;
import com.rbcits.sdata.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/api/pets")
public class PetController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    private final PetRepository petRepository;

    @Autowired
    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Pet get(@PathVariable("id") Pet pet) {
        return pet;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Pet> get() {
        return petRepository.findAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Pet pet, HttpServletRequest request, HttpServletResponse response) {
        Pet createdPet = petRepository.save(pet);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdPet.getId()).toString());
    }

    @RequestMapping(value = "/{petId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long petId) {
        petRepository.delete(petId);
    }



    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTodoNotFound(ResourceNotFoundException ex) {

    }
}
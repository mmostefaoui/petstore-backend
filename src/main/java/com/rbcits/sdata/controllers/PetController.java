package com.rbcits.sdata.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbcits.sdata.controllers.util.GenericResponse;
import com.rbcits.sdata.domain.entities.AppUser;
import com.rbcits.sdata.domain.entities.Pet;
import com.rbcits.sdata.exceptions.ResourceNotFoundException;
import com.rbcits.sdata.services.IPetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/api/pets")
public class PetController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    private final IPetService petService;

    @Autowired
    public PetController(IPetService petService) {
        this.petService = petService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Pet get(@PathVariable("id") Pet pet) {
        LOGGER.info("Find pet.  id={}", pet.getId());
        return pet;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Pet> get() {
        LOGGER.info("Find all pets.");
        return petService.getAllPets();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse create(@RequestBody Pet pet, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Registering pet with information: {}", pet);

        Pet createdPet = petService.addPet(pet);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdPet.getId()).toString());
        return new GenericResponse("success");
    }

    @RequestMapping(value = "/api/pets/{id}", method = RequestMethod.DELETE)
    public GenericResponse delete(@PathVariable("id") Pet pet) {
        // TODO Check authorization before deleting
        petService.deletePet(pet);
        return new GenericResponse("success");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTodoNotFound(ResourceNotFoundException ex) {

    }
}
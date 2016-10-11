package com.rbcits.sdata.controllers;


import com.rbcits.sdata.controllers.util.GenericResponse;
import com.rbcits.sdata.domain.dtos.PetDto;
import com.rbcits.sdata.domain.entities.Pet;
import com.rbcits.sdata.exceptions.ResourceNotFoundException;
import com.rbcits.sdata.services.IPetService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/pets")
public class PetController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    private final IPetService petService;
    private final ModelMapper modelMapper;
    @Autowired
    public PetController(IPetService petService, ModelMapper modelMapper) {
        this.petService = petService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN_UNLIMITED_PRIVILEGE', 'USER_FIND_PRIVILEGE')")
    public Pet get(@PathVariable("id") Pet pet) {
        LOGGER.info("Find pet.  id={}", pet.getId());
        return pet;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN_UNLIMITED_PRIVILEGE')")
    public List<PetDto> get() {
        LOGGER.info("Find all pets.");

        return petService.getAllPets()
                .stream().map(pet -> convertToDto(pet)).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN_UNLIMITED_PRIVILEGE', 'USER_ADD_PRIVILEGE')")
    public GenericResponse create(@RequestBody PetDto petDto, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Registering pet with information: {}", petDto);

        Pet pet = convertToEntity(petDto);

        final Pet createdPet = petService.addPet(pet);

        response.setHeader("Location", request.getRequestURL().append("/").append(createdPet.getId()).toString());
        return new GenericResponse("success");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('ADMIN_UNLIMITED_PRIVILEGE', 'USER_ADD_PRIVILEGE')")
    public PetDto update(@PathVariable("id") Long petId, @RequestBody PetDto petDto) {
        return convertToDto(petService.updatePet(petId, petDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus( HttpStatus.OK )
    @PreAuthorize("hasAnyAuthority('ADMIN_UNLIMITED_PRIVILEGE', 'USER_DELETE_PRIVILEGE')")
    public GenericResponse delete(@PathVariable("id") Pet pet) {
        petService.deletePet(pet);
        return new GenericResponse("success");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTodoNotFound(ResourceNotFoundException ex) {

    }

    private PetDto convertToDto(Pet pet) {
        return modelMapper.map(pet, PetDto.class);
    }

    private Pet convertToEntity(PetDto petDto) throws ParseException {
        return modelMapper.map(petDto, Pet.class);

    }
}
package com.rbcits.sdata.controllers;


import com.rbcits.sdata.controllers.util.GenericResponse;
import com.rbcits.sdata.domain.dtos.PetDto;
import com.rbcits.sdata.domain.entities.Pet;
import com.rbcits.sdata.domain.specifications.PetSpecificationsBuilder;
import com.rbcits.sdata.exceptions.ResourceNotFoundException;
import com.rbcits.sdata.services.IPetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/pets")
public class PetController extends RESTController<Pet, PetDto> {

    private final IPetService petService;

    @Autowired
    protected PetController(ModelMapper modelMapper, IPetService petService) {
        super(modelMapper);
        this.petService = petService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Pet get(@PathVariable("id") Pet pet) {
        LOGGER.info("Find pet.  id={}", pet.getId());
        return pet;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<PetDto> get() {
        LOGGER.info("Find all pets.");

        return petService.getAllPets(null)
                .stream().map(pet -> convertToDto(pet)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/filter/{categoryId}",method = RequestMethod.GET)
    @ResponseBody
    public List<PetDto> get(@PathVariable("categoryId") Long categoryId) {
        LOGGER.info("Find all pets by category.");

        return petService.getAllPetByCategory(categoryId)
                .stream().map(pet -> convertToDto(pet)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/spec", method = RequestMethod.GET)
    @ResponseBody
    public List<PetDto> get(@RequestParam(value = "search") String search) {
        LOGGER.info("Find all pets.");

        PetSpecificationsBuilder builder = new PetSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Pet> spec = builder.build();

        return petService.getAllPets(spec)
                .stream().map(pet -> convertToDto(pet)).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('UNLIMITED_PRIVILEGE', 'ADD_PRIVILEGE')")
    public GenericResponse create(@RequestBody PetDto petDto, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Registering pet with information: {}", petDto);

        Pet pet = convertToEntity(petDto);

        final Pet createdPet = petService.addPet(pet);

        response.setHeader("Location", request.getRequestURL().append("/").append(createdPet.getId()).toString());
        return new GenericResponse("success");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('UNLIMITED_PRIVILEGE', 'UPDATE_PRIVILEGE')")
    public PetDto update(@PathVariable("id") Long petId, @RequestBody PetDto petDto) {
        return convertToDto(petService.updatePet(petId, petDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('UNLIMITED_PRIVILEGE', 'DELETE_PRIVILEGE')")
    public GenericResponse delete(@PathVariable("id") Pet pet) {
        petService.deletePet(pet);
        return new GenericResponse("success");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTodoNotFound(ResourceNotFoundException ex) {

    }

    @Override
    Class<Pet> getTEntityClass() {
        return Pet.class;
    }

    @Override
    Class<PetDto> getTEntityDtoClass() {
        return PetDto.class;
    }
}
package com.rbcits.sdata.controllers;


import com.rbcits.sdata.domain.entities.Pet;
import com.rbcits.sdata.domain.repository.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pets")
public class PetController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    private final PetRepository petRepository;

    @Autowired
    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @ModelAttribute("pet")
    public Pet getBuilding() {
        return new Pet();
    }

    @RequestMapping(value = "/{petId}", method = RequestMethod.GET)
    public Pet get(@PathVariable Long petId) {

        return null;
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Pet> get() {

        return null;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Pet save(@PathVariable Long petId) {

        return null;


    }

    @RequestMapping(value = "/{petId}", method = RequestMethod.DELETE)
    public Pet deletePet(@PathVariable Long petId) {

        return null;


    }

}
package com.rbcits.sdata.controllers;


import com.rbcits.sdata.controllers.util.GenericResponse;
import com.rbcits.sdata.domain.entities.Pet;
import com.rbcits.sdata.domain.repository.PetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/api/init")
public class IniDbtController {

    private final PetRepository petRepository;

    public IniDbtController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse create(@RequestBody List<Pet> pets, HttpServletRequest request, HttpServletResponse response) {

        petRepository.save(pets);
        return new GenericResponse("success");
    }

}

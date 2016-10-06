package com.rbcits.sdata.controllers;

import com.rbcits.sdata.domain.entities.AppUser;
import com.rbcits.sdata.domain.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class AppUserController {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<AppUser> get(){
        return appUserRepository.findAll();
    }
}

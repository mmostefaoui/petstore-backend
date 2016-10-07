package com.rbcits.sdata.controllers;

import com.rbcits.sdata.controllers.util.GenericResponse;
import com.rbcits.sdata.domain.dtos.AppUserDto;
import com.rbcits.sdata.domain.entities.AppUser;
import com.rbcits.sdata.exceptions.ResourceNotFoundException;
import com.rbcits.sdata.services.IAppUserService;
import com.rbcits.sdata.services.IRoleService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/users")
public class AppUserController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    private final IAppUserService appUserService;
    private final IRoleService roleService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserController(IAppUserService appUserService
            , IRoleService roleService
            , ModelMapper modelMapper
            , PasswordEncoder passwordEncoder) {

        this.appUserService = appUserService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AppUserDto get(@PathVariable("id") AppUser user) {
        return convertToDto(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<AppUserDto> get(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        LOGGER.info(principal.getName());
        return appUserService.getAllUsers()
                .stream().map(user -> convertToDto(user)).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse create(@Valid @RequestBody AppUserDto userDto, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Registering user with information: {}", userDto);

        AppUser user = convertToEntity(userDto);
        user.setUserStatus(0);
        user.setRoles(Arrays.asList(roleService.findByName("ROLE_USER")));

        AppUser createdUser = appUserService.registerNewUser(user);

        response.setHeader("Location", request.getRequestURL().append("/").append(createdUser.getId()).toString());
        return new GenericResponse("success");
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public AppUserDto update(@PathVariable("id") Long userId, @RequestBody AppUserDto userDto) {
        return convertToDto(appUserService.updateUser(userId, userDto));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTodoNotFound(ResourceNotFoundException ex) {

    }

    private AppUserDto convertToDto(AppUser user) {
        return modelMapper.map(user, AppUserDto.class);
    }

    private AppUser convertToEntity(AppUserDto userDto) throws ParseException {
        AppUser user = modelMapper.map(userDto, AppUser.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }
}

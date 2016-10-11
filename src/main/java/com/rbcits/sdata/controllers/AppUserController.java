package com.rbcits.sdata.controllers;

import com.rbcits.sdata.controllers.util.GenericResponse;
import com.rbcits.sdata.domain.dtos.AppUserDto;
import com.rbcits.sdata.domain.entities.AppUser;
import com.rbcits.sdata.exceptions.ResourceNotFoundException;
import com.rbcits.sdata.services.IAppUserService;
import com.rbcits.sdata.services.IRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/users")
public class AppUserController extends RESTController<AppUser, AppUserDto> {
    private final IAppUserService appUserService;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    protected AppUserController(ModelMapper modelMapper, IAppUserService appUserService, IRoleService roleService, PasswordEncoder passwordEncoder) {
        super(modelMapper);
        this.appUserService = appUserService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PostAuthorize("returnObject.username == principal.username or hasAuthority('ADMIN_UNLIMITED_PRIVILEGE')")
    @PreAuthorize("hasAnyAuthority('ADMIN_UNLIMITED_PRIVILEGE','USER_FIND_PRIVILEGE')")
    public AppUserDto get(@PathVariable("id") AppUser user) {
        LOGGER.info("Find user.  id={}", user.getId());
        return convertToDto(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN_UNLIMITED_PRIVILEGE')")
    public List<AppUserDto> get() {
        LOGGER.info("Find all users.");
        return appUserService.getAllUsers()
                .stream().map(user -> convertToDto(user)).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse create(@Valid @RequestBody AppUserDto userDto, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Registering user with information: {}", userDto);

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        AppUser user = convertToEntity(userDto);
        user.setUserStatus(0);
        user.setRoles(Arrays.asList(roleService.findByName("ROLE_USER")));

        AppUser createdUser = appUserService.addUser(user);

        response.setHeader("Location", request.getRequestURL().append("/").append(createdUser.getId()).toString());
        return new GenericResponse("success");
    }

    @PostAuthorize("returnObject.username == principal.username or hasAuthority('ADMIN_UNLIMITED_PRIVILEGE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public AppUserDto update(@PathVariable("id") Long userId, @RequestBody AppUserDto userDto) {
        return convertToDto(appUserService.updateUser(userId, userDto));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTodoNotFound(ResourceNotFoundException ex) {

    }

    private boolean checkIfFetchUsers(HttpServletRequest request) {
        return request.isUserInRole("ROLE_ADMIN");
    }

    @Override
    Class<AppUser> getTEntityClass() {
        return AppUser.class;
    }

    @Override
    Class<AppUserDto> getTEntityDtoClass() {
        return AppUserDto.class;
    }
}

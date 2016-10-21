package com.rbcits.sdata.services;

import com.rbcits.sdata.domain.dtos.AppUserDto;
import com.rbcits.sdata.domain.entities.AppUser;
import com.rbcits.sdata.exceptions.ResourceAlreadyExistException;

import java.util.Collection;

public interface IAppUserService {

    AppUser addUser(AppUser user) throws ResourceAlreadyExistException;

    AppUser updateUser(Long userId, AppUserDto userDto);

    void deleteUser(AppUser user);

    AppUser findUserByUsername(String username);

    Collection<AppUser> getAllUsers();

}

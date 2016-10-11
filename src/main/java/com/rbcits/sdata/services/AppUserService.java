package com.rbcits.sdata.services;

import com.rbcits.sdata.domain.dtos.AppUserDto;
import com.rbcits.sdata.domain.entities.AppUser;
import com.rbcits.sdata.domain.repository.AppUserRepository;
import com.rbcits.sdata.domain.repository.RoleRepository;
import com.rbcits.sdata.exceptions.ResourceAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class AppUserService implements IAppUserService {

    private AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser addUser(AppUser user) throws ResourceAlreadyExistException {
        if (userExist(user.getUsername())) {
            throw new ResourceAlreadyExistException("User already exists with that username " + user.getUsername());
        }
        return appUserRepository.save(user);
    }

    @Override
    public AppUser updateUser(Long userId, AppUserDto userDto) {
        AppUser update = appUserRepository.findOne(userId);
        update.setFirstName(userDto.getFirstName());
        update.setLastName(userDto.getLastName());
        update.setEmail(userDto.getEmail());
        update.setPhone(userDto.getPhone());

        return appUserRepository.save(update);
    }

    @Override
    public void deleteUser(AppUser user) {
        appUserRepository.delete(user);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public Collection<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    private boolean userExist(String username) {
        return appUserRepository.findByUsername(username) != null;
    }
}

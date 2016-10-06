package com.rbcits.sdata.domain.repository;

import com.rbcits.sdata.domain.entities.AppUser;


public interface AppUserRepository extends BaseRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    @Override
    void delete(AppUser user);
}

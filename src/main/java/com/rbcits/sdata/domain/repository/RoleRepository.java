package com.rbcits.sdata.domain.repository;


import com.rbcits.sdata.domain.entities.Role;

public interface RoleRepository extends BaseRepository<Role, Long> {
    Role findByName(String name);

    @Override
    void delete(Role role);
}

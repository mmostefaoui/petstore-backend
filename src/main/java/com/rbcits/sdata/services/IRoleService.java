package com.rbcits.sdata.services;


import com.rbcits.sdata.domain.entities.Role;

public interface IRoleService {
    Role findByName(String name);
}

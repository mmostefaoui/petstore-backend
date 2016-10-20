package com.rbcits.sdata.controllers;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ParseException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class RESTController<TEntity, TEntityDto> {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    private final ModelMapper modelMapper;

    abstract Class<TEntity> getTEntityClass();

    abstract Class<TEntityDto> getTEntityDtoClass();

    protected RESTController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected TEntityDto convertToDto(TEntity entity) {
        return modelMapper.map(entity, getTEntityDtoClass());
    }

    protected TEntity convertToEntity(TEntityDto dto) throws ParseException {
        return modelMapper.map(dto, getTEntityClass());
    }

    protected String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }


    protected List<String> getAuthorities() {
        String userName = null;

        final Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<String> authList = new ArrayList<>();
        for (GrantedAuthority auth : authorities) {
           authList.add(auth.getAuthority());
        }
        return authList;
    }

}

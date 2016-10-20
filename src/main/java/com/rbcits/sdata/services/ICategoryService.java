package com.rbcits.sdata.services;


import com.rbcits.sdata.domain.entities.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAll();
}

package com.rbcits.sdata.controllers;

import com.rbcits.sdata.domain.dtos.CategoryDto;
import com.rbcits.sdata.domain.entities.Category;
import com.rbcits.sdata.services.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController extends RESTController<Category, CategoryDto> {

    @Autowired
    private final ICategoryService categoryService;

    protected CategoryController(ModelMapper modelMapper, ICategoryService categoryService) {
        super(modelMapper);
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<CategoryDto> get(){
        LOGGER.info("Find all categories.");

        return categoryService.getAll()
                .stream().map(category -> convertToDto(category)).collect(Collectors.toList());
    }


    @Override
    Class<Category> getTEntityClass() {
        return Category.class;
    }

    @Override
    Class<CategoryDto> getTEntityDtoClass() {
        return CategoryDto.class;
    }
}

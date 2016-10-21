package com.rbcits.sdata.domain.repository;

import com.rbcits.sdata.domain.entities.AppUser;
import com.rbcits.sdata.domain.entities.Category;
import com.rbcits.sdata.domain.entities.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends BaseRepository<Pet, Long> {

    Page<Pet> findAllByOrderByName(Pageable pageable);

    List<Pet> findAllByCategoryId(Long categoryId);

    @Override
    void delete(Pet pet);
}

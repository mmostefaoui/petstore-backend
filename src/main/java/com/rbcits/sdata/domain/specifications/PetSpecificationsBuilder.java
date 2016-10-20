package com.rbcits.sdata.domain.specifications;

import com.rbcits.sdata.domain.entities.Pet;
import com.rbcits.sdata.domain.entities.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;

public class PetSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public PetSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public PetSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Pet> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Pet>> specs = new ArrayList<Specification<Pet>>();
        for (SearchCriteria param : params) {
            specs.add(new PetSpecification(param));
        }

        Specification<Pet> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }
}

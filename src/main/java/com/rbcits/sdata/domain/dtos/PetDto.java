package com.rbcits.sdata.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rbcits.sdata.domain.entities.PetStatus;

import java.util.HashSet;
import java.util.Set;

public class PetDto {

    private Long id;
    private CategoryDto category;
    private String name;
    private Set<String> photoUrls;
    private Set<TagDto> tags = new HashSet<>();
    private PetStatus status;
    @JsonIgnore
    private AppUserDto user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(Set<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public Set<TagDto> getTags() {
        return tags;
    }

    public void setTags(Set<TagDto> tags) {
        this.tags = tags;
    }

    public PetStatus getStatus() {
        return status;
    }

    public void setStatus(PetStatus status) {
        this.status = status;
    }

    public AppUserDto getUser() {
        return user;
    }

    public void setUser(AppUserDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PetDto{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", photoUrls=" + photoUrls +
                ", tags=" + tags +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}

package com.rbcits.sdata.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PETS")
public class Pet implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PET_ID")
    private Long id;

    @ManyToOne(fetch =FetchType.EAGER ,cascade = CascadeType.DETACH)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Column(name = "NAME")
    private String name;

    @ElementCollection
    @CollectionTable(name = "PHOTO_URLS", joinColumns = @JoinColumn(name = "PET_ID"))
    @Column(name = "PHOTO_URL")
    private Set<String> photoUrls;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TAG_PET", joinColumns = @JoinColumn(name = "PET_ID"),
            inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
    private Set<Tag> tags = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    @JsonProperty("status")
    private PetStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private AppUser user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public PetStatus getStatus() {
        return status;
    }

    public void setStatus(PetStatus status) {
        this.status = status;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Pet{" +
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

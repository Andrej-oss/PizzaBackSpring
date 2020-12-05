package com.pizza_shop.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean newPizza;

    private String path;

    @NotBlank
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Lob
    @JsonIgnore
    private byte[] data;

    private int price;
    private String ingredients;
    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "pizza_id")
    private List<Rating> rating;
    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "pizza_id")
    private List<Size> sizes;
    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "pizza_id")
    private List<Comment> comments;

}

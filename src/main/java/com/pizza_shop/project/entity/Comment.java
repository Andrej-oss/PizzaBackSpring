package com.pizza_shop.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String author;
    @NotBlank
    private String tittle;
    @NotBlank
    private String body;

    private int voice;

    @ManyToOne
    @JsonIgnore
    private Pizza pizza;

    @ManyToOne
    @JsonIgnore
    private User user;
}

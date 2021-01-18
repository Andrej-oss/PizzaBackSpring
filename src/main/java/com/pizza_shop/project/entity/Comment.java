package com.pizza_shop.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

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

    private  long date;

    @OneToMany
    @JoinColumn(name = "comment_id")
    private List<Voice> voice;

    @ManyToOne
    @JsonIgnore
    private Pizza pizza;

    @ManyToOne
    @JsonIgnore
    private User user;
}

package com.pizza_shop.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Positive
    private int price;

    private String size;

    @Positive
    private int amount;

    private Double volume;

    private int pizzaId;

    private int drinkId;

    private int snackId;

    private int dessertId;


    @ToString.Exclude
    @ManyToOne
    @JsonIgnore
    private User user;
}

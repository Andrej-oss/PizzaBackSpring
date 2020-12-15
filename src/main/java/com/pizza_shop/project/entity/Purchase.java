package com.pizza_shop.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

    private int amount;
    @Positive
    private int price;
    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    private User user;

    private int pizzaId;
}

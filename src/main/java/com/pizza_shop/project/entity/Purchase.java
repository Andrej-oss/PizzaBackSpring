package com.pizza_shop.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.joda.time.Instant;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

    @Positive
    private Double volume;
    private int amount;
    @Positive
    private double price;
    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    private User user;

    private int pizzaId;

    private int drinkId;

    private int snackId;

    private int dessertId;

    private long date;
}

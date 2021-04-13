package com.pizza_shop.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @Positive
    private Double price;
    @NotBlank
    private String ingredients;

    private int ordersCount;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "pizza_id")
    private List<Rating> rating;
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
    @JsonIgnore
    @JoinColumn(name = "pizza_id")
    private List<Size> sizes;
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
    @JsonIgnore
    @JoinColumn(name = "pizza_id")
    private List<Comment> comments;

}

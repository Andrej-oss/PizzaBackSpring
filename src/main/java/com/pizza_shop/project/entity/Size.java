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
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String name;

    private int diameter;

    private int weight;

    private int price;
    @JsonIgnore
    @Lob
    private byte[] data;

    private String path;

    @ManyToOne
    @JsonIgnore
    private Pizza pizza;
}

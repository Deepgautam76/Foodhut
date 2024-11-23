package com.FoodHut.FoodHut.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String name;

    private String description;

    private Long price;

    @ManyToOne
    private FoodCategory foodCategory;

    @Column(length = 1000)
    @ElementCollection
    private List<String> image;

    private  boolean available;

    @ManyToOne
    private Restaurant restaurant;

    private boolean isVegetarian;
    private boolean isSeasonal;

    @ManyToMany
    private  List<Ingredients> ingredients=new ArrayList<>();

    private Date creationDate;
}

package com.FoodHut.FoodHut.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    private  Food food;

    private int quantity;
    @ElementCollection
    @CollectionTable(name = "cart_item_ingredient", joinColumns =@JoinColumn(name = "cart_item_id"))
    @Column(name = "ingredient")
    @Cascade(CascadeType.ALL)
    private List<String > ingredients;
    private Long totalPrice;
}

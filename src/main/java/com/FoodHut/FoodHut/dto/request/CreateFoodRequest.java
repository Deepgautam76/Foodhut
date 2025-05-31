package com.FoodHut.FoodHut.request;

import com.FoodHut.FoodHut.model.FoodCategory;
import com.FoodHut.FoodHut.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;
    private FoodCategory category;
    private List<String> images;
    private Long restaurantId;
    private boolean vegetarian;
    private  boolean seasonal;
    private  List<IngredientsItem> ingredients;

}

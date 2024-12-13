package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.model.Cart;
import com.FoodHut.FoodHut.model.CartItem;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.request.AddCartItemRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface CartService {

    CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception;

    CartItem updateCartItemQuantity(Long cardItemId,int quantity) throws Exception;

    Cart removeItemFromCart(Long cartItemId,String jwt) throws Exception;

    Long calculateCartTotals(Cart cart) throws Exception;

    Cart findCartById(Long id) throws Exception;

    Cart findCartByUserId(Long userId) throws Exception;

    Cart clearCart(Long userId) throws Exception;

    

}

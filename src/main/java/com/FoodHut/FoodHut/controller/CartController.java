package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.model.Cart;
import com.FoodHut.FoodHut.model.CartItem;
import com.FoodHut.FoodHut.request.AddCartItemRequest;
import com.FoodHut.FoodHut.request.UpdateCartItemRequest;
import com.FoodHut.FoodHut.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;


    //API end-point for addItem in cart
    @PutMapping("/cart/add")
    public ResponseEntity<?> addItemToCard(
            @RequestBody AddCartItemRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        CartItem cartItem=cartService.addItemToCart(req,jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    //API end point for updateCart item quantity
    @PutMapping("/cart-item/update")
    public ResponseEntity<?> updateCartItemQuantity(
            @RequestBody UpdateCartItemRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        CartItem cartItem=cartService.updateCartItemQuantity(req.getCartItemId(),req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    //API end-point for remove Cart item from cart by id
    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<?> removeCartItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {

        Cart cart=cartService.removeItemFromCart(id,jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    //API end-point for clear cart
    @PutMapping("/cart/clear")
    public ResponseEntity<?> clearCart(
            @RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart= cartService.clearCart(jwt);

        return new ResponseEntity<>(cart,HttpStatus.OK);
    }

    //API end-point for find user Cart
    @GetMapping("/cart")
    public ResponseEntity<?> findUserCart(
            @RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart= cartService.findCartByUserId(jwt);

        return new ResponseEntity<>(cart,HttpStatus.OK);
    }



}

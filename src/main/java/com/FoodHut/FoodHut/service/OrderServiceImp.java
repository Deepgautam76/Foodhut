package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.model.*;
import com.FoodHut.FoodHut.repository.*;
import com.FoodHut.FoodHut.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {

        Address shipAddress=order.getDeliveryAddress();

        Address savedAddress=addressRepository.save(shipAddress);

        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }
        Restaurant restaurant=restaurantService.findRestaurantById(order.getRestaurantId());

        Order createOrder=new Order();
        createOrder.setCustomer(user);
        createOrder.setCreatedAt(new Date());
        createOrder.setOrderStatus("Pending");
        createOrder.setDeliveryAddress(savedAddress);
        createOrder.setRestaurant(restaurant);

        Cart cart=cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems=new ArrayList<>();

        for (CartItem cartItem:cart.getItems()){


        }

        return null;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        return null;
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {

    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {
        return null;
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        return null;
    }
}

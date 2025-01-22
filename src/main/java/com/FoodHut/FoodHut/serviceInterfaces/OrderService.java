package com.FoodHut.FoodHut.serviceInterfaces;

import com.FoodHut.FoodHut.model.Order;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.request.OrderRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderService {

    Order createOrder(OrderRequest order, User user) throws Exception;

    Order updateOrder(Long orderId,String orderStatus) throws Exception;

    void cancelOrder(Long orderId) throws Exception;

    List<Order> getUserOrder(Long userId)throws Exception;

    List<Order> getRestaurantOrder(Long restaurantId,String orderStatus)throws Exception;

    Order findOrderById(Long orderId) throws Exception;


}

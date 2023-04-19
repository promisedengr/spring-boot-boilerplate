package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.Order;
import com.nichoshop.main.repository.OrderRepository;

@Service
public class OrderService {
   @Autowired
   OrderRepository orderRepository;

   public Order getOrderById(Long id) {
      return orderRepository.findById(id).get();
   }

   public List<Order> getAllOrders() {
      List<Order> Orders = new ArrayList<Order>();
      orderRepository.findAll().forEach(Order -> Orders.add(Order));
      return Orders;
   }

   public void saveOrUpdate(Order Order) {
      orderRepository.save(Order);
   }

   public void deleteOrderById(Long id) {
      orderRepository.deleteById(id);
   }
}
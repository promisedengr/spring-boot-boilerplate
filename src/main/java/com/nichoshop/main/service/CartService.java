package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.Cart;
import com.nichoshop.main.repository.CartRepository;

@Service
public class CartService {
   @Autowired
   CartRepository cartRepository;

   public Cart getCartById(Long id) {
      return cartRepository.findById(id).get();
   }

   public List<Cart> getAllCarts() {
      List<Cart> Carts = new ArrayList<Cart>();
      cartRepository.findAll().forEach(Cart -> Carts.add(Cart));
      return Carts;
   }

   public void saveOrUpdate(Cart Cart) {
      cartRepository.save(Cart);
   }

   public void deleteCartById(Long id) {
      cartRepository.deleteById(id);
   }
}

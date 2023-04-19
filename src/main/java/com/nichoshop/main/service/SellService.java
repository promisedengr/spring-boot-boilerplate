package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.Sell;
import com.nichoshop.main.repository.SellRepository;

@Service
public class SellService {
   @Autowired
   SellRepository sellRepository;

//    public Sell getSellById(Long id) {
//       return repository.findById(id).get();
//    }

   public List<Sell> getAllSells() {
      List<Sell> Sells = new ArrayList<Sell>();
      sellRepository.findAll().forEach(Sell -> Sells.add(Sell));
      return Sells;
   }

//    public void saveOrUpdate(Sell Sell) {
//       repository.save(Sell);
//    }

//    public void deleteSellById(Long id) {
//       repository.deleteById(id);
//    }
}
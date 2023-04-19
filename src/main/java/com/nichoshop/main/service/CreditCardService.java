package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.CreditCard;
import com.nichoshop.main.repository.CreditCardRepository;

@Service
public class CreditCardService {
   @Autowired
   CreditCardRepository creditCardRepository;

   public CreditCard getCreditCardById(Long id) {
      return creditCardRepository.findById(id).get();
   }

   public List<CreditCard> getAllCreditCards() {
      List<CreditCard> CreditCards = new ArrayList<CreditCard>();
      creditCardRepository.findAll().forEach(CreditCard -> CreditCards.add(CreditCard));
      return CreditCards;
   }

   public void saveOrUpdate(CreditCard CreditCard) {
      creditCardRepository.save(CreditCard);
   }

   public void deleteCreditCardById(Long id) {
      creditCardRepository.deleteById(id);
   }
}

package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.Message;
import com.nichoshop.main.repository.MessageRepository;

@Service
public class MessageService {
   @Autowired
   MessageRepository messageRepository;

   public Message getMessageById(Long id) {
      return messageRepository.findById(id).get();
   }

   public List<Message> getAllMessages() {
      List<Message> Messages = new ArrayList<Message>();
      messageRepository.findAll().forEach(Message -> Messages.add(Message));
      return Messages;
   }

   public void saveOrUpdate(Message Message) {
      messageRepository.save(Message);
   }

   public void deleteMessageById(Long id) {
      messageRepository.deleteById(id);
   }
}

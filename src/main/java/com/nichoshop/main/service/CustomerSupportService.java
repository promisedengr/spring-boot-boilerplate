package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.repository.AdminNoteRepository;
import com.nichoshop.main.repository.CustomerSupportRepository;
import com.nichoshop.main.request.AdminNoteCreateRequest;
import com.nichoshop.main.request.CSCreateRequest;
import com.nichoshop.main.model.CustomerSupport;
import com.nichoshop.main.model.AdminNote;

@Service
public class CustomerSupportService {
   @Autowired
   CustomerSupportRepository cSupportRepository;
   @Autowired
   AdminNoteRepository adminNoteRepository;

   public CustomerSupport getCustomerSupportById(Long id) {
      return cSupportRepository.findById(id).get();
   }

   public List<CustomerSupport> getAllCustomerSupports() {
      List<CustomerSupport> CustomerSupports = new ArrayList<CustomerSupport>();
      cSupportRepository.findAll().forEach(CustomerSupport -> CustomerSupports.add(CustomerSupport));
      return CustomerSupports;
   }

   public List<AdminNote> getAdminNoteBycsId(Long csId) {
      return adminNoteRepository.findByCsId(csId);

   }

   public void createAdminNote(Long adminId, AdminNoteCreateRequest adminNoteCreateReq) {
      AdminNote adminNote = new AdminNote();
      adminNote.setSubject(adminNoteCreateReq.getSubject());
      adminNote.setDesc(adminNoteCreateReq.getDesc());
      adminNote.setCsId(adminNoteCreateReq.getCsId());
      adminNote.setCreatorId(adminId);
      adminNoteRepository.save(adminNote);
   }

   public void updateAdminNote(Long noteId, AdminNoteCreateRequest adminNoteCreateReq) {
      AdminNote adminNote = adminNoteRepository.findById(noteId).orElse(null);
      if (adminNote != null) {
         adminNote.setSubject(adminNoteCreateReq.getSubject());
         adminNote.setDesc(adminNoteCreateReq.getDesc());
         adminNote.setCsId(adminNoteCreateReq.getCsId());
      }
      adminNoteRepository.save(adminNote);
   }

   public void saveOrUpdate(CustomerSupport CustomerSupport) {
      cSupportRepository.save(CustomerSupport);
   }

   public void deleteCustomerSupportById(Long id) {
      cSupportRepository.deleteById(id);
   }
}

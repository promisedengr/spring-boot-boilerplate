// package com.nichoshop.main.service;

// import java.util.ArrayList;
// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import com.nichoshop.main.entity.AdminNote;
// import com.nichoshop.main.repository.AdminNoteRepo;


// @Service
// public class AdminNoteService {
//    @Autowired
//    AdminNoteRepo repository;

//    public AdminNote getAdminNoteById(Long id) {
//       return repository.findById(id).get();
//    }

//    public List<AdminNote> getAllAdminNotes() {
//       List<AdminNote> AdminNotes = new ArrayList<AdminNote>();
//       repository.findAll().forEach(AdminNote -> AdminNotes.add(AdminNote));
//       return AdminNotes;
//    }

//    public void saveOrUpdate(AdminNote AdminNote) {
//       repository.save(AdminNote);
//    }

//    public void deleteAdminNoteById(Long id) {
//       repository.deleteById(id);
//    }
// }

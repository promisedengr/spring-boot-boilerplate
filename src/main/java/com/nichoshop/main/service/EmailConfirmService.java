package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nichoshop.main.model.EmailConfirm;
import com.nichoshop.main.repository.EmailConfirmRepository;

@Service
public class EmailConfirmService {
    @Autowired
    EmailConfirmRepository repository;

    public EmailConfirm getEmailConfirmById(Long id) {
        return repository.findById(id).get();
    }

    public List<EmailConfirm> getAllEmailConfirms() {
        List<EmailConfirm> EmailConfirms = new ArrayList<EmailConfirm>();
        repository.findAll().forEach(EmailConfirm -> EmailConfirms.add(EmailConfirm));
        return EmailConfirms;
    }

    public void saveOrUpdate(EmailConfirm EmailConfirm) {
        repository.save(EmailConfirm);
    }

    public void deleteEmailConfirmById(Long id) {
        repository.deleteById(id);
    }
}

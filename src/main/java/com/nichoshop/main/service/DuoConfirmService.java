package com.nichoshop.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.DuoConfirm;
import com.nichoshop.main.repository.DuoConfirmRepository;

@Service
public class DuoConfirmService {
    @Autowired
    DuoConfirmRepository duoConfirmRepository;

    public DuoConfirm getDuoByState(String state){
        return duoConfirmRepository.findByState(state);
    }

    public void deleteById(Long id){
        duoConfirmRepository.deleteById(id);
    }

    public void saveOrUpdate(DuoConfirm duoConfirm) {
        duoConfirmRepository.save(duoConfirm);
     }
}

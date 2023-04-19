package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.SUC;
import com.nichoshop.main.repository.SUCRepository;

@Service
public class SUCService {
    @Autowired
    SUCRepository sucRepository;

    // public SUC getSUCById(Long id) {
    // return repository.findById(id).get();
    // }

    public List<String> getSUCsByUserId(Long userId) {
        return sucRepository.findByUserId(userId);
    }

    public void saveOrUpdate(SUC SUC) {
        sucRepository.save(SUC);
    }

    public SUC findLastSUC(Long userId, Integer sucType) {
        return sucRepository.findLastWithIdAndType(userId, sucType);
    }

    public Boolean checkSUC(Long userId, String code, Integer sucType) {
        SUC suc = sucRepository.findSUC(userId, code, sucType);
        if (suc != null)
            return true;
        else
            return false;
    }
}

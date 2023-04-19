package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.FeedBack;
import com.nichoshop.main.repository.FeedBackRepository;

@Service
public class FeedBackService {
    @Autowired
    FeedBackRepository feedBackRepository;

    public FeedBack getFeedBackById(Long id) {
        return feedBackRepository.findById(id).get();
    }

    public List<FeedBack> getAllFeedBacks() {
        List<FeedBack> FeedBacks = new ArrayList<FeedBack>();
        feedBackRepository.findAll().forEach(FeedBack -> FeedBacks.add(FeedBack));
        return FeedBacks;
    }

    public void saveOrUpdate(FeedBack FeedBack) {
        feedBackRepository.save(FeedBack);
    }

    public void deleteFeedBackById(Long id) {
        feedBackRepository.deleteById(id);
    }
}

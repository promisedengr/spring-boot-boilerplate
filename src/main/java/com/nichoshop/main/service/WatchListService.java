package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.WatchList;
import com.nichoshop.main.repository.WatchListRepository;

@Service
public class WatchListService {
    @Autowired
    WatchListRepository watchListRepository;

    public WatchList getWatchListById(Long id) {
        return watchListRepository.findById(id).get();
    }

    public List<WatchList> getAllWatchList() {
        List<WatchList> watchLists = new ArrayList<WatchList>();
        watchListRepository.findAll().forEach(watchList -> watchLists.add(watchList));
        return watchLists;
    }

    public void saveOrUpdate(WatchList watchList) {
        watchListRepository.save(watchList);
    }

    public void deleteWatchListById(Long id) {
        watchListRepository.deleteById(id);
    }
}
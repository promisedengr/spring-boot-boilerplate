package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nichoshop.main.model.ItemView;
import com.nichoshop.main.repository.ItemViewRepository;

@Service
public class ItemViewService {
    @Autowired
    ItemViewRepository repository;

    public ItemView getItemViewById(Long id) {
        return repository.findById(id).get();
    }

    public List<ItemView> getAllItemViews() {
        List<ItemView> ItemViews = new ArrayList<ItemView>();
        repository.findAll().forEach(ItemView -> ItemViews.add(ItemView));
        return ItemViews;
    }

    public void saveOrUpdate(ItemView ItemView) {
        repository.save(ItemView);
    }

    public void deleteItemViewById(Long id) {
        repository.deleteById(id);
    }
}

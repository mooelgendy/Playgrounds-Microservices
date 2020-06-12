package com.elgendy.storeservice.service;

import com.elgendy.storeservice.model.Store;
import com.elgendy.storeservice.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService{

    private StoreRepository repository;

    @Autowired
    public StoreServiceImpl(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Store> getAll() {
        List<Store> itemList = repository.findAll();
        return itemList;
    }

    @Override
    public Store getOne(Integer id) {
        Store storeById = repository.findById(id);
        return storeById;
    }

    @Override
    public void add(Store item) {
        repository.save(item);
    }

    @Override
    public void update(Store item) {
        repository.update(item);
    }

    @Override
    public void delete(Integer id) {
        Store deletedItem = repository.findById(id);
        repository.delete(deletedItem);
    }
}

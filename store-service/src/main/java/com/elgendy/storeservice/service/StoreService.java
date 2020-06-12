package com.elgendy.storeservice.service;

import com.elgendy.storeservice.model.Store;

import java.util.List;

public interface StoreService {

    List<Store> getAll();

    Store getOne(Integer id);

    void add(Store item);

    void update(Store item);

    void delete(Integer id);
}

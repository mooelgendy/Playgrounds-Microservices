package com.elgendy.photoservice.service;

import com.elgendy.photoservice.model.Photo;

import java.util.List;

public interface PhotoService {

    List<Photo> getAll();

    Photo getOne(Integer id);

    void add(Photo photo);

    void update(Photo photo);

    void delete(Integer id);
}

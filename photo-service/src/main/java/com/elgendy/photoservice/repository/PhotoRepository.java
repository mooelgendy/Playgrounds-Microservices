package com.elgendy.photoservice.repository;

import com.elgendy.photoservice.model.Photo;

import java.util.List;

public interface PhotoRepository {

    List<Photo> findAll();

    Photo findById(Integer id);

    void save(Photo photo);

    void update(Photo photo);

    void delete(Photo photo);
}

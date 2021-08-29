package com.elgendy.photoservice.service;

import com.elgendy.photoservice.model.Photo;
import com.elgendy.photoservice.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository repository;

    @Override
    public List<Photo> getAll() {
        List<Photo> photoList = repository.findAll();
        return photoList;
    }

    @Override
    public Photo getOne(Integer id) {
        Photo photoById = repository.findById(id);
        return photoById;
    }

    @Override
    public void add(Photo photo) {
        repository.save(photo);
    }

    @Override
    public void update(Photo photo) {
        repository.update(photo);
    }

    @Override
    public void delete(Integer id) {
        Photo deletedPhoto = repository.findById(id);
        repository.delete(deletedPhoto);
    }
}

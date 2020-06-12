package com.elgendy.photoservice.controller;

import com.elgendy.photoservice.model.dto.PhotoDTO;
import com.elgendy.photoservice.model.Photo;
import com.elgendy.photoservice.service.PhotoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/photo")
public class PhotoController implements Serializable {

    private PhotoService service;
    private static Logger LOGGER = LoggerFactory.getLogger(PhotoController.class);

    @Autowired
    public PhotoController(PhotoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<PhotoDTO> getAll(){
        List<Photo> photos = null;
        List<PhotoDTO> photoDTOs = null;
        Iterator<Photo> it = null;
        try{
            photos = service.getAll();
            photoDTOs = new ArrayList<>();
            it = photos.iterator();
            while(it.hasNext()){
                Photo photo = it.next();
                PhotoDTO dto = new PhotoDTO();
                dto.setId(photo.getId());
                dto.setName(photo.getName());
                dto.setLink(photo.getLink());
                dto.setPlaygroundId(photo.getPlaygroundId());
                dto.setStoreId(photo.getStoreId());
                dto.setTeamId(photo.getTeamId());
                dto.setUserId(photo.getUserId());
                photoDTOs.add(dto);
            }
            return photoDTOs;
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @GetMapping("/{id}")
    public PhotoDTO findOne(@PathVariable("id") Integer id){
        Photo photo = null;
        PhotoDTO dto = null;
        try{
            photo = service.getOne(id);
            dto = new PhotoDTO();
            dto.setId(photo.getId());
            dto.setLink(photo.getLink());
            dto.setName(photo.getName());
            dto.setPlaygroundId(photo.getPlaygroundId());
            dto.setStoreId(photo.getStoreId());
            dto.setTeamId(photo.getTeamId());
            dto.setUserId(photo.getUserId());
            return dto;
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody PhotoDTO dto) {
        Photo photo = null;
        try{
            photo = new Photo();
            photo.setName(dto.getName());
            photo.setLink(dto.getLink());
            photo.setPlaygroundId(dto.getPlaygroundId());
            photo.setStoreId(dto.getStoreId());
            photo.setUserId(dto.getUserId());
            photo.setTeamId(dto.getTeamId());
            service.add(photo);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody PhotoDTO dto) {
        Photo photo = null;
        try{
            photo = new Photo();
            photo.setId(dto.getId());
            photo.setName(dto.getName());
            photo.setLink(dto.getLink());
            photo.setPlaygroundId(dto.getPlaygroundId());
            photo.setStoreId(dto.getStoreId());
            photo.setUserId(dto.getUserId());
            photo.setTeamId(dto.getTeamId());
            service.update(photo);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        try{
            service.delete(id);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }
}

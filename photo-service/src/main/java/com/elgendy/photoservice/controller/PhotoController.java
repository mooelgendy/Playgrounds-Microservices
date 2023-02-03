package com.elgendy.photoservice.controller;

import com.elgendy.photoservice.exception.InternalServerErrorException;
import com.elgendy.photoservice.model.Photo;
import com.elgendy.photoservice.model.dto.PhotoDTO;
import com.elgendy.photoservice.service.PhotoService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/api/photo")
public class PhotoController implements Serializable {

    private final PhotoService service;

    @GetMapping("/")
    public List<PhotoDTO> getAll(){
        List<Photo> photos;
        List<PhotoDTO> photoDTOs;
        try{
            photos = service.getAll();
            photoDTOs = photos.stream().map(photo -> {
                PhotoDTO dto = new PhotoDTO();
                dto.setId(photo.getId());
                dto.setName(photo.getName());
                dto.setLink(photo.getLink());
                dto.setPlaygroundId(photo.getPlaygroundId());
                dto.setStoreId(photo.getStoreId());
                dto.setTeamId(photo.getTeamId());
                dto.setUserId(photo.getUserId());
                return dto;
            }).collect(Collectors.toList());
            return photoDTOs;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @GetMapping("/{id}")
    public PhotoDTO findOne(@PathVariable("id") Integer id){
        Photo photo;
        PhotoDTO dto;
        try{
            photo = service.getOne(id);
            if(photo == null){
                return null;
            }
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
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody PhotoDTO dto) {
        Photo photo;
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
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody PhotoDTO dto) {
        Photo photo;
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
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        try{
            service.delete(id);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }
}

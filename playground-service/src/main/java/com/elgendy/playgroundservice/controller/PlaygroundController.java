package com.elgendy.playgroundservice.controller;

import com.elgendy.playgroundservice.exception.InternalServerErrorException;
import com.elgendy.playgroundservice.model.dto.PlaygroundDTO;
import com.elgendy.playgroundservice.model.Playground;
import com.elgendy.playgroundservice.service.PlaygroundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/playground")
public class PlaygroundController implements Serializable {

    private PlaygroundService service;
    private static Logger LOGGER = LoggerFactory.getLogger(PlaygroundController.class);

    @Autowired
    public PlaygroundController(PlaygroundService service) {
        this.service = service;
    }

    @GetMapping("/")
    @Cacheable(value= "playgroundsListCache", unless= "#result.size() == 0")
    public List<PlaygroundDTO> getAll(){
        List<Playground> playgrounds = null;
        List<PlaygroundDTO> playgroundDTOs = null;
        try{
            playgrounds = service.getAll();
            playgroundDTOs = playgrounds.stream().map(playground -> {
                PlaygroundDTO dto = new PlaygroundDTO();
                dto.setId(playground.getId());
                dto.setName(playground.getName());
                dto.setAddress(playground.getAddress());
                dto.setArea(playground.getArea());
                dto.setAvailableTime(playground.getAvailableTime());
                dto.setDescription(playground.getDescription());
                dto.setPhone(playground.getPhone());
                dto.setPricePerHour(playground.getPricePerHour());
                return dto;
            }).collect(Collectors.toList());
            return playgroundDTOs;
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @GetMapping("/{id}")
    @Cacheable(value = "playgroundCache")
    public PlaygroundDTO findOne(@PathVariable("id") Integer id){
        Playground playground = null;
        PlaygroundDTO dto = null;
        try{
            playground = service.getOne(id);
            if(playground == null){
                return null;
            }
            dto = new PlaygroundDTO();
            dto.setId(playground.getId());
            dto.setName(playground.getName());
            dto.setPhone(playground.getPhone());
            dto.setArea(playground.getArea());
            dto.setAddress(playground.getAddress());
            dto.setPricePerHour(playground.getPricePerHour());
            dto.setAvailableTime(playground.getAvailableTime());
            dto.setDescription(playground.getDescription());
            return dto;
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @CachePut(value= "playgroundCache")
    public void create(@RequestBody PlaygroundDTO dto) {
        Playground playground = null;
        try{
            playground = new Playground();
            playground.setName(dto.getName());
            playground.setArea(dto.getArea());
            playground.setAddress(dto.getAddress());
            playground.setAvailableTime(dto.getAvailableTime());
            playground.setDescription(dto.getDescription());
            playground.setPhone(dto.getPhone());
            playground.setPricePerHour(dto.getPricePerHour());
            service.add(playground);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @CachePut(value= "playgroundCache")
    public void update(@RequestBody PlaygroundDTO dto) {
        Playground playground = null;
        try{
            playground = new Playground();
            playground.setId(dto.getId());
            playground.setName(dto.getName());
            playground.setAddress(dto.getAddress());
            playground.setArea(dto.getArea());
            playground.setAvailableTime(dto.getAvailableTime());
            playground.setDescription(dto.getDescription());
            playground.setPricePerHour(dto.getPricePerHour());
            playground.setPhone(dto.getPhone());
            service.update(playground);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value= "playgroundCache")
    public void delete(@PathVariable("id") Integer id) {
        try{
            service.delete(id);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }
}

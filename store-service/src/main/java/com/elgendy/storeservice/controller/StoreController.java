package com.elgendy.storeservice.controller;

import com.elgendy.storeservice.exception.InternalServerErrorException;
import com.elgendy.storeservice.model.Store;
import com.elgendy.storeservice.model.dto.StoreDTO;
import com.elgendy.storeservice.service.StoreService;
import com.elgendy.storeservice.service.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService service;
    private final UserInfo userInfo;

    @GetMapping("/")
    public List<StoreDTO> getAll(){
        List<Store> items;
        List<StoreDTO> itemsDTOs;
        Iterator<Store> it;
        try{
            items = service.getAll();
            itemsDTOs = items.stream().map(item -> {
                StoreDTO dto = new StoreDTO();
                dto.setId(item.getId());
                dto.setName(item.getName());
                dto.setDescription(item.getDescription());
                dto.setSerialNumber(item.getSerialNumber());
                dto.setPrice(item.getPrice());
                dto.setUserDTO(userInfo.getUserDTO(item));
                return dto;
            }).collect(Collectors.toList());
            return itemsDTOs;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @GetMapping("/{id}")
    public StoreDTO findOne(@PathVariable("id") Integer id){
        Store item;
        StoreDTO dto;
        try{
            item = service.getOne(id);
            if(item == null){
                return null;
            }
            dto = new StoreDTO();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setDescription(item.getDescription());
            dto.setSerialNumber(item.getSerialNumber());
            dto.setPrice(item.getPrice());
            dto.setUserId(item.getUserId());
            return dto;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody StoreDTO dto) {
        Store item;
        try{
            item = new Store();
            item.setName(dto.getName());
            item.setDescription(dto.getDescription());
            item.setSerialNumber(dto.getSerialNumber());
            item.setPrice(dto.getPrice());
            item.setUserId(dto.getUserId());
            service.add(item);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody StoreDTO dto) {
        Store item;
        try{
            item = new Store();
            item.setId(dto.getId());
            item.setName(dto.getName());
            item.setDescription(dto.getDescription());
            item.setSerialNumber(dto.getSerialNumber());
            item.setPrice(dto.getPrice());
            item.setUserId(dto.getUserId());
            service.update(item);
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
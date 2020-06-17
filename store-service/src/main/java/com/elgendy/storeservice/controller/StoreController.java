package com.elgendy.storeservice.controller;

import com.elgendy.storeservice.model.dto.StoreDTO;
import com.elgendy.storeservice.model.Store;
import com.elgendy.storeservice.model.dto.UserDTO;
import com.elgendy.storeservice.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/store")
public class StoreController {

    private StoreService service;
    private static Logger LOGGER = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    public StoreController(StoreService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<StoreDTO> getAll(){
        List<Store> items = null;
        List<StoreDTO> itemsDTOs = null;
        Iterator<Store> it = null;
        try{
            items = service.getAll();
            itemsDTOs = items.stream().map(item -> {
                StoreDTO dto = new StoreDTO();
                dto.setId(item.getId());
                dto.setName(item.getName());
                dto.setDescription(item.getDescription());
                dto.setSerialNumber(item.getSerialNumber());
                dto.setPrice(item.getPrice());
                UserDTO userDTO = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8087/playgrounds/api/user/" + item.getUserId())
                        .retrieve()
                        .bodyToMono(UserDTO.class)
                        .block();
                LOGGER.info("UserDTO: {}", userDTO.toString());
                dto.setUserDTO(userDTO);
                return dto;
            }).collect(Collectors.toList());
            return itemsDTOs;
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @GetMapping("/{id}")
    public StoreDTO findOne(@PathVariable("id") Integer id){
        Store item = null;
        StoreDTO dto = null;
        try{
            item = service.getOne(id);
            dto = new StoreDTO();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setDescription(item.getDescription());
            dto.setSerialNumber(item.getSerialNumber());
            dto.setPrice(item.getPrice());
            dto.setUserId(item.getUserId());
            return dto;
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody StoreDTO dto) {
        Store item = null;
        try{
            item = new Store();
            item.setName(dto.getName());
            item.setDescription(dto.getDescription());
            item.setSerialNumber(dto.getSerialNumber());
            item.setPrice(dto.getPrice());
            item.setUserId(dto.getUserId());
            service.add(item);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody StoreDTO dto) {
        Store item = null;
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
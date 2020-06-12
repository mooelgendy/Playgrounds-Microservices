package com.elgendy.userservice.controller;

import com.elgendy.userservice.model.dto.UserDTO;
import com.elgendy.userservice.model.User;
import com.elgendy.userservice.service.UserService;
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
@RequestMapping("/api/user")
public class UserController implements Serializable {

    private UserService service;
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<UserDTO> getAll(){
        List<User> users = null;
        List<UserDTO> userDTOs = null;
        Iterator<User> it = null;
        try {
            users = service.getAll();
            userDTOs = new ArrayList<>();
            it = users.iterator();
            while(it.hasNext()){
                User user = it.next();
                UserDTO dto = new UserDTO();
                dto.setId(user.getId());
                dto.setName(user.getName());
                dto.setAddress(user.getAddress());
                dto.setPosition(user.getPosition());
                dto.setPhone(user.getPhone());
                dto.setBio(user.getBio());
                userDTOs.add(dto);
            }
            return userDTOs;
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @GetMapping("/{id}")
    public UserDTO findOne(@PathVariable("id") Integer id){
        User user = null;
        UserDTO dto = null;
        try{
            user = service.getOne(id);
            dto = new UserDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setAddress(user.getAddress());
            dto.setBio(user.getBio());
            dto.setPhone(user.getPhone());
            dto.setPosition(user.getPosition());
            return dto;
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserDTO dto) {
        User user = null;
        try{
            user = new User();
            user.setName(dto.getName());
            user.setAddress(dto.getAddress());
            user.setBio(dto.getBio());
            user.setPhone(dto.getPhone());
            user.setPosition(dto.getPosition());
            service.add(user);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody UserDTO dto) {
        User user = null;
        try{
            user = new User();
            user.setId(dto.getId());
            user.setName(dto.getName());
            user.setAddress(dto.getAddress());
            user.setBio(dto.getBio());
            user.setPhone(dto.getPhone());
            user.setPosition(dto.getPosition());
            service.update(user);
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

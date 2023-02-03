package com.elgendy.userservice.controller;

import com.elgendy.userservice.exception.InternalServerErrorException;
import com.elgendy.userservice.model.User;
import com.elgendy.userservice.model.dto.UserDTO;
import com.elgendy.userservice.service.UserService;
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
@RequestMapping("/api/user")
public class UserController implements Serializable {

    private final UserService service;

    @GetMapping("/")
    public List<UserDTO> getAll(){
        List<User> users;
        List<UserDTO> userDTOs;
        try {
            users = service.getAll();
            userDTOs = users.stream().map(user -> {
                UserDTO dto = new UserDTO();
                dto.setId(user.getId());
                dto.setName(user.getName());
                dto.setAddress(user.getAddress());
                dto.setPosition(user.getPosition());
                dto.setPhone(user.getPhone());
                dto.setBio(user.getBio());
                return dto;
            }).collect(Collectors.toList());
            return userDTOs;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred");
        }
    }

    @GetMapping("/{id}")
    public UserDTO findOne(@PathVariable("id") Integer id){
        User user;
        UserDTO dto;
        try{
            user = service.getOne(id);
            if(user == null){
                return null;
            }
            dto = new UserDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setAddress(user.getAddress());
            dto.setBio(user.getBio());
            dto.setPhone(user.getPhone());
            dto.setPosition(user.getPosition());
            return dto;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred");
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserDTO dto) {
        User user;
        try{
            user = new User();
            user.setName(dto.getName());
            user.setAddress(dto.getAddress());
            user.setBio(dto.getBio());
            user.setPhone(dto.getPhone());
            user.setPosition(dto.getPosition());
            service.add(user);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred");
        }
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody UserDTO dto) {
        User user;
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
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred");
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        try{
            service.delete(id);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred");
        }
    }
}

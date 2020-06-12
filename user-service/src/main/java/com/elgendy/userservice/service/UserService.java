package com.elgendy.userservice.service;

import com.elgendy.userservice.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getOne(Integer id);

    void add(User user);

    void update(User user);

    void delete(Integer id);
}

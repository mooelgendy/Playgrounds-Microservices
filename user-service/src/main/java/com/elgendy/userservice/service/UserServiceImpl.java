package com.elgendy.userservice.service;

import com.elgendy.userservice.model.User;
import com.elgendy.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<User> getAll(){
        List<User> userList = repository.findAll();
        return userList;
    }

    @Override
    public User getOne(Integer id) {
        User userById = repository.findById(id);
        return userById;
    }

    @Override
    public void add(User user) {
        repository.save(user);
    }

    @Override
    public void update(User user) {
        repository.update(user);
    }

    @Override
    public void delete(Integer id) {
        User deletedUser = repository.findById(id);
        repository.delete(deletedUser);
    }
}

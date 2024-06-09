package com.smart.services;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
   private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
       return this.userRepository.findAll();
    }

    @Override
    public User registerUser(User user) {
        this.userRepository.save(user);
        return user;
    }
}

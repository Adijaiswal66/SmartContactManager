package com.smart.services;

import com.smart.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService{

    public List<User> getAllUsers();

    public User registerUser(User user);
}

package com.testtask.service;

import com.testtask.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void createUser(User user);
    User readUser(int id);
    void updateUser(User user);
    void deleteUser(int id);
    List<User> getUsersByName(String name);
    List<User> getUsers(Long page);
}

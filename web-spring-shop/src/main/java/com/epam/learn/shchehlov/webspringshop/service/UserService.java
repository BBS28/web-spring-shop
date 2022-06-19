package com.epam.learn.shchehlov.webspringshop.service;

import com.epam.learn.shchehlov.webspringshop.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService{

    User findUserByLogin(String login);

    List<User> findAllUsers();

    User createUser(User user);

    void deleteUser(User user);

    void deleteAllUsers();


}

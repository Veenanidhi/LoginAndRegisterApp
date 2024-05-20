package com.example.LoginAppSB.service;

import com.example.LoginAppSB.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User findById(Long id);

    void save(User user);

    void saveUp(User user);

    List<User> findAll();

    void deleteById(Long id);
    User findByUsername(String username);


}

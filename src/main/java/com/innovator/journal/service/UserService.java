package com.innovator.journal.service;

import com.innovator.journal.Entity.User;
import com.innovator.journal.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepo;

    private final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public User findByName(String userName){
       return userRepo.findByUserName(userName);
    }

    public void insertNewUser(User userData) {
        try {
            userData.setPassword(passwordEncoder.encode(userData.getPassword()));
            userData.setRoles(Arrays.asList("USER"));
            userRepo.save(userData);
        } catch (Exception e) {
            log.error("The was an error while inserting new user {} ",userData.getUserName(),e);
        }
    }

    public void insertAdminUser(User userData) {
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        userData.setRoles(Arrays.asList("USER","ADMIN"));
        userRepo.save(userData);
    }



    public void  insertUser(User userData) {
        userRepo.save(userData);
    }

    public void deleteUser(String userName){
        userRepo.deleteByUserName(userName);
    }

    public List<User> findAllUser(){
        return userRepo.findAll();
    }
}

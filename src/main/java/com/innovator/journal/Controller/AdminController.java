package com.innovator.journal.Controller;

import com.innovator.journal.ApplicationCache.Cache;
import com.innovator.journal.Entity.User;
import com.innovator.journal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    Cache cache;

    @GetMapping
    public ResponseEntity<?> getAllUser(){
        List<User>users=userService.findAllUser();
        if(!users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("No user exist in the database. ",HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> updateAdmin(@RequestBody User user){
        SecurityContext context= SecurityContextHolder.getContext();
        String userName=context.getAuthentication().getName();
        User old=userService.findByName(userName);
        if(!user.getUserName().isEmpty() && !user.getPassword().isEmpty()){
            old.setUserName(user.getUserName());
            old.setPassword(user.getPassword());
            userService.insertAdminUser(old);
        }
        return new ResponseEntity<>("The Admin "+userName+" has been updated ",HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAdmin(){
        SecurityContext context=SecurityContextHolder.getContext();
        String userName=context.getAuthentication().getName();
        userService.deleteUser(userName);
        return new ResponseEntity<>("The admin "+userName+" has been removed from database.",HttpStatus.NO_CONTENT);
    }

    @GetMapping("clear-app-cache")
    public ResponseEntity<String> clearCache(){
        try {
            cache.cacheInitializer();
            return new ResponseEntity<>("App cache has been cleared and re-Initialized.", HttpStatus.OK);
        } catch (Exception e) {
            log.info("while initializing cache something went wrong. cache initialization failed");
            throw new RuntimeException(e);
        }
    }
}

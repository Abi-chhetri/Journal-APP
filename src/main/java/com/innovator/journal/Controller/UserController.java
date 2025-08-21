package com.innovator.journal.Controller;

import com.innovator.journal.Entity.User;
import com.innovator.journal.PojoForApiResponse.Quote;
import com.innovator.journal.service.QuoteService;
import com.innovator.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    QuoteService quoteService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){

        SecurityContext context= SecurityContextHolder.getContext();
        String userName=context.getAuthentication().getName();
        User userIndb=userService.findByName(userName);
        userIndb.setUserName(user.getUserName());
        userIndb.setPassword(user.getPassword());
        userService.insertNewUser(userIndb);

        return new ResponseEntity<>("The user has been successfully updated with userName "+user.getUserName(),HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(){
        SecurityContext context=SecurityContextHolder.getContext();
        String userName=context.getAuthentication().getName();
        userService.deleteUser(userName);
        return new ResponseEntity<>("User "+ userName+" has been successfully removed from database.",HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<String> greet(){
        SecurityContext context=SecurityContextHolder.getContext();
        String userName=context.getAuthentication().getName();
        Quote fetchedQuote=quoteService.fetchQuote();
        String data="";
       if(fetchedQuote !=null){
           data=" \nQuote of the day: "+ fetchedQuote.getQuote()+"\nAuthor: "+fetchedQuote.getAuth()+"\nCategory: "+fetchedQuote.getCategory();
           return new ResponseEntity<>("Hi "+userName+data,HttpStatus.OK);
       }
       return new ResponseEntity<>("Something went wrong while fetching the Api",HttpStatus.BAD_REQUEST);
    }
}

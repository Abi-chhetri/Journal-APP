package com.innovator.journal.Controller;

import com.innovator.journal.Entity.Email;
import com.innovator.journal.Entity.User;
import com.innovator.journal.service.EmailService;
import com.innovator.journal.service.UserDetailServiceImpl;
import com.innovator.journal.service.UserService;
import com.innovator.journal.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sign-up")
@Slf4j
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody User user){
        User userInDb= userService.findByName(user.getUserName());
        if(userInDb ==null ){
            userService.insertNewUser(user);
            return new ResponseEntity<>("Your Account has been created successfully.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("The user name is already taken please choose different one.",HttpStatus.IM_USED);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{

            authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);

        } catch (AuthenticationException e) {
            log.error("Something went wrong while loging the user. {}", String.valueOf(e));
            return new ResponseEntity<>("Incorrect UserName or Password.",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin(@RequestBody User user){
        User userInDb=userService.findByName(user.getUserName());
        List<String> roles=user.getRoles();
        if(userInDb == null){
            userService.insertAdminUser(user);
            return new ResponseEntity<>("The admin has been successfully creted.",HttpStatus.CREATED);
        }

        return new ResponseEntity<>("The user name is already taken please choose different one.",HttpStatus.IM_USED);
    }

    @PostMapping("sendmail")
    public ResponseEntity<String> sendMail(@RequestBody Email email){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        try{
            emailService.sendMail(email.getTo(),email.getSubject(),email.getBody());
            return new ResponseEntity<>("Mail has been sent successFully to "+ email.getTo(),HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("Something went wrong while sending email "+ e);
            return new ResponseEntity<>("Something went wrong while sending email.",HttpStatus.BAD_REQUEST);
        }
    }
}

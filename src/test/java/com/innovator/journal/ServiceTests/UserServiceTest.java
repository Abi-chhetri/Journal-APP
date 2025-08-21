package com.innovator.journal.ServiceTests;


import com.innovator.journal.Entity.User;
import com.innovator.journal.repository.UserRepository;
import com.innovator.journal.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @ParameterizedTest
    @ValueSource(strings=
            {
                    "Abi Chhetri",
                    "Prishma Dahal",
                    "Ram",
            })
    void testFindByName(String name){
        User user=userService.findByName(name);
        assertNotNull(user," Test of "+name);
    }

    @Test
    void testFindAllUser(){
        User users=userService.findAllUser().get(0);
        if(users != null ){
            assertNotNull(users);
        }
        else{
            assertNull(users);
        }
    }

}

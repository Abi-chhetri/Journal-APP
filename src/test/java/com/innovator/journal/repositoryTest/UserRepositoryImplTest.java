package com.innovator.journal.repositoryTest;


import com.innovator.journal.Entity.User;
import com.innovator.journal.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserRepositoryImplTest {
    @Autowired
    UserRepositoryImpl userRepositoryImpl;

    @Test
    public void fetchSentimentalAnalysisTest(){
        List<User> users=userRepositoryImpl.fetchSentimentalAnalysis();
        Assertions.assertNotNull(users);
       for(User user:users){
           System.out.println(user.getUserName());
           System.out.println(user.getEmail());
       }
    }
}

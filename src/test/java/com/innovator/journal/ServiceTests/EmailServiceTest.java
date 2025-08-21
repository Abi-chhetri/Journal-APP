package com.innovator.journal.ServiceTests;

import com.innovator.journal.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendMailTest(){
        emailService.sendMail("acbp890@gmail.com","Testing java mail sender.",
                "Hello, this is a test email sent from Spring Boot!");
    }

}

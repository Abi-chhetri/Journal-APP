package com.innovator.journal.Entity;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Email {
    private String to;
    private String subject;
    private String body;
}

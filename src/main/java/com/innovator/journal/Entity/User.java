package com.innovator.journal.Entity;

import com.innovator.journal.enums.Sentiments;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "users")
@Getter
@Setter
public class User {
    @Id
    private ObjectId id;
    @NonNull
    @Indexed(unique = true)
    private String userName;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry> userJournal=new ArrayList<>();
    private List<String> roles;
    private String email;
    private List<Sentiments> sentiments;
    private boolean sentimentAnalysis;
}

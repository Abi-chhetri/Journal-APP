package com.innovator.journal.service;

import com.innovator.journal.Entity.JournalEntry;
import com.innovator.journal.Entity.User;
import com.innovator.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalRepo;
    @Autowired
    private UserService userService;

    @Transactional
    public void insertNewEntry(JournalEntry newEntry, String userName){
        try {
            User data=userService.findByName(userName);
            JournalEntry saved=journalRepo.save(newEntry);
            data.getUserJournal().add(saved);
            userService.insertUser(data);
        } catch (Exception e) {
            throw new RuntimeException("An Error occurred while adding the entry ",e);
        }
    }

    public void updateEntry(JournalEntry entry){
        journalRepo.save(entry);
    }

    @Transactional
    public void deleteById(ObjectId id, String userName){
        try{
            User data=userService.findByName(userName);
            data.getUserJournal().removeIf(x->x.getId().equals(id));
            journalRepo.deleteById(id);
            userService.insertUser(data);
        }
        catch(Exception e){
            throw new RuntimeException("An error occured while deleting the entry "+e);
        }
    }


   //no connection with user below this . they are for plane journal finding and deleting
    public List<JournalEntry> getAllEntries(){
        return journalRepo.findAll();
    }
    
    public Optional<JournalEntry> getEntryById(ObjectId id){
        return journalRepo.findById(id);
    }

    public void clearDocuments(){
        journalRepo.deleteAll();
    }

}

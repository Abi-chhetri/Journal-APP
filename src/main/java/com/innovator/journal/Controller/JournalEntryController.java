package com.innovator.journal.Controller;

import com.innovator.journal.Entity.JournalEntry;
import com.innovator.journal.Entity.User;
import com.innovator.journal.service.JournalEntryService;
import com.innovator.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        SecurityContext context=SecurityContextHolder.getContext();
        String userName=context.getAuthentication().getName();

        List<JournalEntry> data=userService.findByName(userName).getUserJournal();
        if(data != null && !data.isEmpty()){
            return new ResponseEntity<>(data,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createUserEntry(@RequestBody JournalEntry entry){
        try{
            SecurityContext context=SecurityContextHolder.getContext();
            String userName=context.getAuthentication().getName();

            if(!entry.getTitle().isEmpty() && !entry.getContent().isEmpty()){
                entry.setDate(LocalDateTime.now());
                journalEntryService.insertNewEntry(entry,userName);
                return new ResponseEntity<>(entry,HttpStatus.CREATED);
            }
            return  new ResponseEntity<>("Body wasn't given",HttpStatus.LENGTH_REQUIRED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/id/{id}")
    public ResponseEntity<String> updateEntry(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry updateEntry
    ){
        SecurityContext context= SecurityContextHolder.getContext();
        String userName=context.getAuthentication().getName();
        User user= userService.findByName(userName);
        List<JournalEntry> found= user.getUserJournal().stream().filter(x->  x.getId().equals(id)).toList();

        if( !found.isEmpty()){
            Optional<JournalEntry>old =journalEntryService.getEntryById(id);

            if(old.isPresent() && !updateEntry.getContent().isEmpty() && !updateEntry.getTitle().isEmpty() ){
                old.get().setContent(updateEntry.getContent());
                old.get().setTitle(updateEntry.getTitle());
                journalEntryService.updateEntry(old.get());
                return new ResponseEntity<>("The entry has been updated successfully of user "+userName,HttpStatus.OK);
            }

        }
        return new ResponseEntity<>("The body is required with all the field",HttpStatus.LENGTH_REQUIRED);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteEntry(@PathVariable ObjectId id){

        SecurityContext context=SecurityContextHolder.getContext();
        String userName=context.getAuthentication().getName();
        List<JournalEntry>isUserJournal=userService.findByName(userName).getUserJournal().stream().filter(x -> x.getId().equals(id)).toList();

        if(!isUserJournal.isEmpty()){
            Optional<JournalEntry> find=journalEntryService.getEntryById(id);
            if(find.isPresent()){
                journalEntryService.deleteById(id,userName);
                return new ResponseEntity<>("Successfully deleted the user with id "+id,HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>("user not found",HttpStatus.NOT_FOUND);
    }



    //this  below two api's are for plane journal doesn't have any connection with user
    //here we have to pass the object id of the journal entry
    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getUserEntryById(@PathVariable ObjectId id){
        SecurityContext context= SecurityContextHolder.getContext();
        String userName=context.getAuthentication().getName();
        User userData=userService.findByName(userName);
        if(!userData.getUserJournal().isEmpty()){
            List <JournalEntry>data=userData.getUserJournal().stream().filter(x -> x.getId().equals(id)).toList();
            return new ResponseEntity<>(data.getFirst(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteEntries")
    public ResponseEntity<String> deleteAllEntries(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        User user= userService.findByName(auth.getName());
        List<String>isAdmin=user.getRoles().stream().filter(x-> x.equals("ADMIN")).collect(Collectors.toList());

        if(!isAdmin.isEmpty()){
            List<JournalEntry>db=journalEntryService.getAllEntries();
            if(!db.isEmpty()){
                journalEntryService.clearDocuments();
                return new ResponseEntity<>("All User Entries has been cleared",HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>("The DataBase is empty",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Only admins are allowed to use this endpoint.",HttpStatus.BAD_REQUEST);
    }
}

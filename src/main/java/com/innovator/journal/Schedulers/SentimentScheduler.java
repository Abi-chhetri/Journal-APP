package com.innovator.journal.Schedulers;

import com.innovator.journal.Entity.JournalEntry;
import com.innovator.journal.Entity.User;
import com.innovator.journal.enums.Sentiments;
import com.innovator.journal.repository.UserRepositoryImpl;
import com.innovator.journal.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SentimentScheduler {
    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 9 * * SAT")
    public void sentimentScheduler(){
        List<User> users= userRepositoryImpl.fetchSentimentalAnalysis();
        for(User user: users){
            List <JournalEntry> entries= user.getUserJournal();
            List<Sentiments> sentiments= entries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x-> x.getSentiment()).collect(Collectors.toList());

            Map<Sentiments, Integer> sentimentCounts=new HashMap<>();
            for(Sentiments sentiment: sentiments){
                sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment, 0)+1);
            }


            Sentiments mostFrequentSentiment=null;
            int max=0;

            for(Map.Entry<Sentiments, Integer> entry: sentimentCounts.entrySet()){
                if(entry.getValue() >max){
                    max=entry.getValue();
                    mostFrequentSentiment=entry.getKey();
                }
            }


            if(mostFrequentSentiment!=null){
                emailService.sendMail(user.getEmail(),"Your mood of last week", mostFrequentSentiment.toString());
            }

        }
    }
}

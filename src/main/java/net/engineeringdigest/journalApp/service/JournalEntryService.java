package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserEntryService userEntryService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        User user = userEntryService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(journalEntry);
        userEntryService.saveUser(user);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(String userName, ObjectId id) {
        boolean removed = false;
        try {
            User user = userEntryService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                journalEntryRepository.deleteById(id);
                userEntryService.saveUser(user);
            }
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting the entry", e);
        }

        return removed;
    }
}

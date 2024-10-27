package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserEntryService userEntryService;

    public void saveEntry(JournalEntry journalEntry, String userName)
    {
        User user = userEntryService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(journalEntry);
        userEntryService.saveEntry(user);
    }

    public void saveEntry(JournalEntry journalEntry)
    {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll()
    {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id)
    {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(String userName, ObjectId id)
    {
        User user  = userEntryService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        journalEntryRepository.deleteById(id);
        userEntryService.saveEntry(user);
    }
}

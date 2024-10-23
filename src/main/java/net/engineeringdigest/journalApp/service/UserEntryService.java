package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {

    @Autowired
    private UserEntryRepository userEntryRepository;

    public void saveEntry(User userEntry)
    {
        userEntryRepository.save(userEntry);
    }

    public List<User> getAll()
    {
        return userEntryRepository.findAll();
    }

    public Optional<User> findById(ObjectId id)
    {
        return userEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id)
    {
        userEntryRepository.deleteById(id);
    }

    public User findByUserName(String userName)
    {
        return userEntryRepository.findByUserName(userName);
    }
}

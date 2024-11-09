package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {

    @Autowired
    private UserEntryRepository userEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewEntry(User userEntry)
    {
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER"));
        userEntryRepository.save(userEntry);
    }

    public void saveUser(User user){
        userEntryRepository.save(user);
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

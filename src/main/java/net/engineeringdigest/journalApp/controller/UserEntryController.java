package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserEntryController {

   @Autowired
   private UserEntryService userService;

   @GetMapping
   public List<User> getAllUsers()
   {
       return userService.getAll();
   }

   @PostMapping
   public void createUser(@RequestBody User user)
   {
       userService.saveEntry(user);
   }

   @PutMapping("/{userName}")
   public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName)
   {
       User userInDb = userService.findByUserName(userName);
       if(userInDb != null)
       {
           userInDb.setUserName(user.getUserName());
           userInDb.setPassword(user.getPassword());
           userService.saveEntry(userInDb);
           return new ResponseEntity<>(HttpStatus.OK);
       }

       return new ResponseEntity<>(HttpStatus.NOT_FOUND);


   }
}

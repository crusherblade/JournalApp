package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserEntryRepository;
import net.engineeringdigest.journalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserEntryController {

   @Autowired
   private UserEntryService userService;

   @Autowired
   private UserEntryRepository userEntryRepository;

   @PutMapping
   public ResponseEntity<?> updateUser(@RequestBody User user)
   {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String userName = authentication.getName();
       User userInDb = userService.findByUserName(userName);

       userInDb.setUserName(user.getUserName());
       userInDb.setPassword(user.getPassword());
       userService.saveNewEntry(userInDb);
       return new ResponseEntity<>(HttpStatus.OK);

   }

   @DeleteMapping
   public ResponseEntity<?> deleteUser(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       userEntryRepository.deleteByUserName(authentication.getName());
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
}

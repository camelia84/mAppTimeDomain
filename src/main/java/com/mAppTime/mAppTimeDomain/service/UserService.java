package com.mAppTime.mAppTimeDomain.service;

import com.mAppTime.mAppTimeDomain.dao.UserRepository;
import com.mAppTime.mAppTimeDomain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }
    public List<User> findUsersByName(){
        return userRepository.findByOrderByFirstNameAsc();
    }
    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }
    public User insertUser(User user){
        return userRepository.save(user);
    }

    public ResponseEntity<User> updateUser(User inUser){
        try{
          User newUser =  userRepository.findById(inUser.getUserId()).orElseThrow(RuntimeException::new);
          newUser.setFirstName(inUser.getFirstName());
          newUser.setLastName(inUser.getLastName());
          newUser.setNumberPhone(inUser.getNumberPhone());
          newUser.setEvents(inUser.getEvents());
          newUser.setContactId(inUser.getContactId());
          userRepository.save(newUser);
          return ResponseEntity.ok(newUser);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    public void deleteAll(){
        userRepository.deleteAll();
    }
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}

package com.mAppTime.mAppTimeDomain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.mAppTime.mAppTimeDomain.entity.User;
import com.mAppTime.mAppTimeDomain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findUserById(id),HttpStatus.OK);
    }
    @GetMapping("/{order}")
    public ResponseEntity<List<User>> findUsersByName(){
        return new ResponseEntity<>(userService.findUsersByName(),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<User> insertUser(@RequestBody User user){
        return new ResponseEntity<>(userService.insertUser(user),HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody JsonPatch inUser){
        try{
            User user = userService.findUserById(id).orElseThrow(RuntimeException::new);
            User userPatched = applyPatchToUser(inUser, user);
            userService.updateUser(userPatched);
            return ResponseEntity.ok(userPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll(){
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable  Long id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private User applyPatchToUser(JsonPatch patch, User targeUser) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targeUser, JsonNode.class));
        return objectMapper.treeToValue(patched, User.class);
    }
}

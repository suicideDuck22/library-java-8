package com.levelup.library.controllers;

import com.levelup.library.entities.UserEntity;
import com.levelup.library.services.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class User {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping()
    ResponseEntity<Map<String, Collection<UserEntity>>> findUsers(@RequestParam(required = false) Long id){
        Map<String, List<UserEntity>> users = new HashMap<>();
        users.put("users", userService.find(id));
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @PostMapping(
            value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Map<String, String>> createUser(@RequestBody @Valid UserEntity newUser){
        Map<String, String> responseObject = new HashMap<>();
        userService.create(newUser);

        responseObject.put("message", "User created successfully.");
        return new ResponseEntity(responseObject, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        Map<String, String> responseObject = new HashMap<>();
        userService.delete(id);

        responseObject.put("message", "User with ID " + id + " has been deleted.");
        return new ResponseEntity(responseObject, HttpStatus.OK);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Map<String, String>> updateUser(@RequestBody @Valid UserEntity updatedUser, @PathVariable Long id){
        Map<String, String> responseobject = new HashMap<>();
        userService.update(id, updatedUser);
        responseobject.put("message", "User updated successfully.");
        return new ResponseEntity(responseobject, HttpStatus.OK);
    }
}

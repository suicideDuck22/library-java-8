package com.levelup.library.controllers;

import com.levelup.library.entities.UserEntity;
import com.levelup.library.repositories.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class Login {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/login")
    ResponseEntity<UserEntity> login() {
        return null;
    }

    @PostMapping(
            value = "/authenticate",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<Map<String, String>> authenticate(@RequestBody UserEntity user) {
        Map<String, String> responseObject = new HashMap<>();
        String hashedPassword = DigestUtils.sha256Hex(user.getPassword());
        Optional<UserEntity> returnedUser = Optional.ofNullable(userRepository.findByEmailAndPassword(user.getEmail(), hashedPassword));

        if(returnedUser.isPresent()){
            responseObject.put("name", returnedUser.get().getName());
            responseObject.put("nickname", returnedUser.get().getNickname());
            responseObject.put("message", "Authentication successfully.");
            return new ResponseEntity(responseObject, HttpStatus.OK);
        }

        responseObject.put("error", "Authentication failed");
        responseObject.put("message", "Email or password incorrect.");
        return new ResponseEntity(responseObject, HttpStatus.UNAUTHORIZED);
    }
}

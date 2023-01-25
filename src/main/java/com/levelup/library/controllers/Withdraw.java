package com.levelup.library.controllers;

import com.levelup.library.entities.WithdrawEntity;
import com.levelup.library.enums.WithdrawStatus;
import com.levelup.library.services.WithdrawServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/withdraw")
public class Withdraw {
    @Autowired
    private WithdrawServiceImpl withdrawService;

    @GetMapping()
    ResponseEntity<Map<String, Collection<WithdrawEntity>>> getAll(@RequestParam Long id, @RequestParam(required = false) WithdrawStatus status){
        Map<String, List<WithdrawEntity>> responseObject = new HashMap<>();
        List<WithdrawEntity> withdraws = withdrawService.getWithdrawsByUser(id, status);

        responseObject.put("withdraws", withdraws);
        return new ResponseEntity(responseObject, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Map<String, WithdrawEntity>> get(@PathVariable Long id){
        Map<String, WithdrawEntity> responseObject = new HashMap<>();
        responseObject.put("withdraw", withdrawService.find(id));

        return new ResponseEntity(responseObject, HttpStatus.OK);
    }
}

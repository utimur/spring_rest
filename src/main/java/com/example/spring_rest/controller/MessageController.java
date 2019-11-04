package com.example.spring_rest.controller;


import com.example.spring_rest.exceptions.NotFoundException;
import com.example.spring_rest.models.Message;
import com.example.spring_rest.repos.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    MessageRepository messageRepository;
    ObjectMapper mapper = new ObjectMapper();


    @GetMapping
    public ResponseEntity<Iterable<Message>> getMessages() throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccessControlAllowOrigin("*");
        return new ResponseEntity<Iterable<Message>>( messageRepository.findAll()  ,httpHeaders, HttpStatus.OK);
    }

    @GetMapping("{id}")
    private ResponseEntity<Message> getOne(@PathVariable Long id) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccessControlAllowOrigin("*");
        return new ResponseEntity<Message>(messageRepository.findById(id).get() ,httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccessControlAllowOrigin("*");
        messageRepository.deleteById(id);
        return new ResponseEntity<String>("delete",httpHeaders,HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Message> create(@RequestParam String text) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccessControlAllowOrigin("*");
        Message message = new Message(text);
        message.setDate(LocalDateTime.now());
        messageRepository.save(message);
        return new ResponseEntity<Message>(message,httpHeaders,HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id,
                                         @RequestParam String text) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccessControlAllowOrigin("*");
        Message message = messageRepository.findById(id).get();
        message.setText(text);
        messageRepository.save(message);
        return new ResponseEntity<String>("update",httpHeaders,HttpStatus.OK);
    }
}
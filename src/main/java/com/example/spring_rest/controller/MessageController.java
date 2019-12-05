package com.example.spring_rest.controller;


import com.example.spring_rest.models.Message;
import com.example.spring_rest.repos.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("message")
@CrossOrigin
public class MessageController {

    @Autowired
    MessageRepository messageRepository;


    @GetMapping
    public Map getMessages(@RequestParam(defaultValue = "1", required = false) Integer page,
                                         @RequestParam(defaultValue = "5", required = false) Integer pagecount ) throws JsonProcessingException {
        List<Message> messages = messageRepository.findAll().stream().sorted(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                if(o1.getId() > o2.getId()) return 1;
                if(o1.getId() < o2.getId()) return -1;
                if(o1.getId() == o2.getId()) return 0;
                return 0;
            }
        }).collect(Collectors.toList());
        page--;
        messages = messages.stream().skip(page*pagecount).limit(pagecount).collect(Collectors.toList());
        Map map = new HashMap();
        map.put("messages", messages);
        map.put("messageCount",messageRepository.findAll().size());
        return map;
    }

    @GetMapping("{id}")
    private Message getOne(@PathVariable Long id) throws JsonProcessingException {
        return messageRepository.findById(id).get();
    }

    @DeleteMapping("{id}")
    public Message delete(@PathVariable Long id) {
        Message message = messageRepository.findById(id).get();
        messageRepository.deleteById(id);
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message mess) {
        Message message = new Message(mess.getText());
        message.setDate(LocalDateTime.now());
        messageRepository.save(message);
        return message;
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @RequestParam String text) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccessControlAllowOrigin("*");
        Message message = messageRepository.findById(id).get();
        message.setText(text);
        messageRepository.save(message);
        return new ResponseEntity<String>("update",httpHeaders,HttpStatus.OK);
    }
}
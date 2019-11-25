package com.example.spring_rest.controller;

import com.example.spring_rest.models.User;
import com.example.spring_rest.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
@CrossOrigin
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        if (checkForm(user) || userRepository.findByUsername(user.getUsername()) != null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        if (checkForm(user)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        User bdUser = userRepository.findByUsername(user.getUsername());
        if (!bdUser.getPassword().equals(user.getPassword())) {
            System.out.println("IF");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bdUser, HttpStatus.OK);
    }

    private boolean checkForm(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return true;
        }
        return false;
    }
}

package com.example.spring_rest.repos;

import com.example.spring_rest.models.Message;
import org.springframework.data.repository.CrudRepository;


public interface MessageRepository extends CrudRepository<Message, Long> {
}

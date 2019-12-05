package com.example.spring_rest.repos;

import com.example.spring_rest.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findAll();
}

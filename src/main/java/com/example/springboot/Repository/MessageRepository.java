package com.example.springboot.Repository;

import com.example.springboot.Entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message,Long> { }
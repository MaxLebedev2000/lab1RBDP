package com.example.springboot.repository;

import com.example.springboot.entity.Message;
import com.example.springboot.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message,Long> {
    @Query("select m from Message m")
    List<Message> findAllMessages();

    @Query("select m from Message m where m.user = :user")
    List<Message> findByLogin (@Param("user") Users user);

    Optional<Message> findById (long id);

    @Query("select m from Message m where m.text = :text")
    Optional<Message> findByText (@Param("text") String text);
}
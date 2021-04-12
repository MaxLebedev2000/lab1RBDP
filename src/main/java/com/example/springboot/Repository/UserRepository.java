package com.example.springboot.Repository;

import com.example.springboot.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String username);

    @Query("select u from User u where u.isModerator = true")
    List<User> findByRole();
}
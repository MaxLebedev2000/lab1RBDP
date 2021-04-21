package com.example.springboot.Repository;

import com.example.springboot.Entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Long> {
    @Query("select u from Users u")
    List<Users> findAllUsers();

    Optional<Users> findByLogin (String username);

    @Query("select u from Users u where u.isModerator = true")
    List<Users> findByRole();
}
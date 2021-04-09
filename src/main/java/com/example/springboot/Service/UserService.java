package com.example.springboot.Service;

import com.example.springboot.DTO.UserDTO;
import com.example.springboot.Entity.User;
import com.example.springboot.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public User getById(int id) {
        return usersRepository.findById((long) id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public User getByLogin(String login) {
        return usersRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(login)));
    }

    public List<User> getModerators() {
        return usersRepository.findByRole();
    }

    public User newUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.login);
        user.setPassword(passwordEncoder.encode(userDTO.password));
        user.setModerator(false);

        return usersRepository.save(user);
    }

    public User newModerator(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.login);
        user.setPassword(passwordEncoder.encode(userDTO.password));
        user.setModerator(true);

        return usersRepository.save(user);
    }
}

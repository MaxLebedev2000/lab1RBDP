package com.example.springboot.Service;

import com.example.springboot.DTO.UserDTO;
import com.example.springboot.Entity.Users;
import com.example.springboot.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository usersRepository;

    public List<Users> getUsers () {
        return usersRepository.findAllUsers();
    }

    public Users getById(int id) {
        return usersRepository.findById((long) id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public Users getByLogin(String login) {
        return usersRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(login)));
    }

    public List<Users> getModerators() {
        return usersRepository.findByRole();
    }

    public Users newUser(UserDTO userDTO) {
        Users user = new Users();
        user.setLogin(userDTO.login);
        user.setPassword(userDTO.password);
        user.setModerator(false);

        return usersRepository.save(user);
    }

    public Users newModerator (UserDTO userDTO) {
        Users user = new Users();
        user.setLogin(userDTO.login);
        user.setPassword(userDTO.password);
        user.setModerator(true);

        return usersRepository.save(user);
    }

    public void addModerator(String login) {
        Users user = usersRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(login)));
        user.setModerator(true);
        usersRepository.save(user);
    }

    public void changeRating (int a, Users user) {
        user.setRating(user.getRating() + a);
    }
}

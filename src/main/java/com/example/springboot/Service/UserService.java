package com.example.springboot.Service;

import com.example.springboot.DTO.UserDTO;
import com.example.springboot.Entity.Users;
import com.example.springboot.Repository.UserRepository;
import com.example.springboot.Repository.XMLRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.List;

import static com.example.springboot.Entity.Role.MODERATOR;
import static com.example.springboot.Entity.Role.USER;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository usersRepository;
    private final UserTransaction userTransaction;


    public List<Users> getUsers() {
        return usersRepository.findAllUsers();
    }

    @PreAuthorize("hasAuthority('users:read')")
    public Users getById(int id) {
        return usersRepository.findById((long) id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    //    @PreAuthorize("hasAuthority('users:read')")
    public Users getByLogin(String login) {
        return usersRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(login)));
    }

    @PreAuthorize("hasAuthority('users:write')")
    public List<Users> getModerators() {
        return usersRepository.findByRole();
    }

    public Users newUser(UserDTO userDTO) {
       try {
            userTransaction.begin();
            Users user = new Users();
            user.setLogin(userDTO.login);
            user.setPassword(userDTO.password);
            user.setRole(USER);
            user = usersRepository.save(user);
            List<Users> allUsers =  this.getUsers();

            XMLRepository xmlRepository = new XMLRepository();
            xmlRepository.write(allUsers);
                userTransaction.commit();

            return user;
           } catch (Exception e) {
               try {
                   userTransaction.rollback();
               } catch (SystemException systemException) {
                    systemException.printStackTrace();
                }

               e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "При выполнении транзакции возникла ошибка", e);
        }
    }

    @PreAuthorize("hasAuthority('users:write')")
    public Users newModerator(UserDTO userDTO) throws SystemException {
        try {
            userTransaction.begin();
            Users user = new Users();
            user.setLogin(userDTO.login);
            user.setPassword(userDTO.password);
            user.setRole(MODERATOR);
            user = usersRepository.save(user);
            userTransaction.commit();

            return user;

        } catch (Exception e) {
            try {
                userTransaction.rollback();
            } catch (SystemException systemException) {
                systemException.printStackTrace();
            }
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "При выполнении транзакции возникла ошибка", e);
        }
    }

    @PreAuthorize("hasAuthority('users:write')")
    public void addModerator(String login) {
        try {
            userTransaction.begin();
            Users user = usersRepository.findByLogin(login)
                    .orElseThrow(() -> new EntityNotFoundException(String.valueOf(login)));
            user.setRole(MODERATOR);
            usersRepository.save(user);
            userTransaction.commit();


        } catch (Exception e) {
            try {
                userTransaction.rollback();
            } catch (SystemException systemException) {
                systemException.printStackTrace();
            }
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "При выполнении транзакции возникла ошибка", e);
        }

    }

    @PreAuthorize("hasAuthority('users:write')")
    public void changeRating(int a, Users user) {
        try {
            userTransaction.begin();
            user.setRating(user.getRating() + a);
            userTransaction.commit();
        } catch (Exception e) {
            try {
                userTransaction.rollback();
            } catch (SystemException systemException) {
                systemException.printStackTrace();
            }
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "При выполнении транзакции возникла ошибка", e);
        }

    }

    @PreAuthorize("hasAuthority('users:write')")
    public void deleteUser(int id) {

        try {
            userTransaction.begin();
            Users user = usersRepository.findById((long) id)
                    .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

            deleteUser(user);
            userTransaction.commit();
        } catch (Exception e) {
            try {
                userTransaction.rollback();
            } catch (SystemException systemException) {
                systemException.printStackTrace();
            }
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "При выполнении транзакции возникла ошибка", e);
        }

    }

    public void deleteUser(Users user) {
        usersRepository.delete(user);
    }
}
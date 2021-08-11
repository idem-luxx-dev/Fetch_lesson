package web.repository;

import org.springframework.validation.BindingResult;
import web.model.User;

import java.util.List;

public interface UserRepo {
    void addUser(User user, BindingResult bind);

    void deleteUser(Long id);

    void editUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    User getUserByUsername(String username);

}

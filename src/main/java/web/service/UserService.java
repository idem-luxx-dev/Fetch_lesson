package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import web.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user, BindingResult bind);

    void deleteUser(Long id);

    void editUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();
    UserDetailsService getDetailsService();
}

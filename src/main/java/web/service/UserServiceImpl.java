package web.service;

import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import web.model.User;
import web.repository.UserRepo;
import web.service.UserService;

import javax.persistence.LockModeType;
import java.util.List;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserDetailsService detailsService;

    public UserServiceImpl(){}

    @Override
    public void addUser(User user, BindingResult bind) {
        userRepo.addUser(user, bind);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteUser(id);
    }

    @Override
    public void editUser(User user) {
        userRepo.editUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }
    @Override
    public UserDetailsService getDetailsService(){
        return detailsService;
    }
}

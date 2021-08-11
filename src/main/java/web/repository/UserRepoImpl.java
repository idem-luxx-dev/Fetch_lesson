package web.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.BindingResult;
import web.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

import web.repository.UserRepo;


@Repository
public class UserRepoImpl implements UserRepo {

    @PersistenceContext
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public void addUser(User user, BindingResult bind) {
        if(!bind.hasFieldErrors()) {
            getEntityManager().persist(user);
        } else {
            System.out.println("Password is less then six symbols!");
        }
    }

    @Override
    public void deleteUser(Long id) {

        try {
            User user = getEntityManager().find(User.class, id);
            if (user != null) {
                getEntityManager().remove(user);
            }
        } catch (NullPointerException e) {
            System.out.println("Косяк, нет таких!");
        }
    }

    @Override
    public void editUser(User user) {
        getEntityManager().merge(user);
    }

    @Override
    public User getUserById(Long id) {
        return getEntityManager().find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {

        return getEntityManager()
                .createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public User getUserByUsername(String username) {
        return getEntityManager()
                .createQuery("select u from User u left join fetch u.roles where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}


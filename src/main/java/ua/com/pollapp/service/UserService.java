package ua.com.pollapp.service;

import ua.com.pollapp.model.User;
import ua.com.pollapp.to.UserTo;
import ua.com.pollapp.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void update(User user) throws NotFoundException;

    void update(UserTo userTo) throws NotFoundException;

    void delete(int userId) throws NotFoundException;

    User findById(int userId) throws NotFoundException;

    List<User> findAll();

    User findByEmail(String email) throws NotFoundException;

    void enable(int id, boolean enabled);
}

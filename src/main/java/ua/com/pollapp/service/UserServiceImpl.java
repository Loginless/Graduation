package ua.com.pollapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ua.com.pollapp.AuthorizedUser;
import ua.com.pollapp.model.User;
import ua.com.pollapp.repository.UserRepository;
import ua.com.pollapp.to.UserTo;
import ua.com.pollapp.util.exception.NotFoundException;

import java.util.List;

import static ua.com.pollapp.util.UserUtil.updateFromTo;
import static ua.com.pollapp.util.ValidationUtil.checkNotFound;
import static ua.com.pollapp.util.ValidationUtil.checkNotFoundWithId;


@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Sort SORT_NAME_EMAIL = new Sort(Sort.Direction.ASC, "name", "email");

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userRepository.save(user), user.getId());
    }

    @Transactional
    @Override
    public void update(UserTo userTo) {
        User user = findById(userTo.getId());
        userRepository.save(updateFromTo(user, userTo));
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void delete(int userId) throws NotFoundException {
        checkNotFoundWithId(userRepository.delete(userId) != 0, userId);
    }

    @Cacheable("users")
    @Override
    public List<User> findAll() {
        return userRepository.findAll(SORT_NAME_EMAIL);
    }

    @Cacheable("users")
    @Override
    public User findById(int userId) throws NotFoundException {
        return checkNotFoundWithId(userRepository.findById(userId).orElse(null), userId);
    }

    @Override
    public User findByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepository.findByEmail(email), "email=" + email);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    @Transactional
    public void enable(int id, boolean enabled) {
        User user = findById(id);
        user.setEnabled(enabled);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}

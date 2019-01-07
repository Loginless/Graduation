package ua.com.pollapp.web.controller.UserController;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.pollapp.model.User;
import ua.com.pollapp.service.UserService;
import ua.com.pollapp.to.UserTo;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ua.com.pollapp.util.ValidationUtil.assureIdConsistent;
import static ua.com.pollapp.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    private static final Logger LOG = getLogger(AbstractUserController.class);

    @Autowired
    private UserService userService;

    public List<User> getAll() {
        LOG.info("getAll");
        return userService.findAll();
    }

    public User get(int id) {
        LOG.info("get {}", id);
        return userService.findById(id);
    }

    public User create(User user) {
        LOG.info("create {}", user);
        checkNew(user);
        return userService.create(user);
    }

    public void delete(int id) {
        LOG.info("delete {}", id);
        userService.delete(id);
    }

    public void update(User user, int id) {
        LOG.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        userService.update(user);
    }

    public void update(UserTo userTo, int id) {
        LOG.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        userService.update(userTo);
    }

    public User getByMail(String email) {
        LOG.info("getByEmail {}", email);
        return userService.findByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        LOG.info(enabled ? "enable {}" : "disable {}", id);
        userService.enable(id, enabled);
    }
}
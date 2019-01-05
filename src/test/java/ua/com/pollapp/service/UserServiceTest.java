package ua.com.pollapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ua.com.pollapp.model.Role;
import ua.com.pollapp.model.User;
import ua.com.pollapp.util.JpaUtil;
import ua.com.pollapp.util.exception.NotFoundException;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ua.com.pollapp.testdata.UserTestData.*;


class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = userService.create(new User(newUser));
        newUser.setId(created.getId());
        assertMatch(userService.findAll(), ADMIN, newUser, USER1, USER2);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                userService.create(new User(null, "Duplicate", "user1@yandex.ru", "newPass", Role.ROLE_USER)));
    }

    @Test
    void update() {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setEmail("test@gmail.com");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        userService.update(new User(updated));
        assertMatch(userService.findById(USER1_ID), updated);
    }

    @Test
    void delete() {
        userService.delete(USER1_ID);
        userService.delete(USER2_ID);
        assertMatch(userService.findAll(), ADMIN);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                userService.delete(1));
    }

    @Test
    void findById() {
        User user = userService.findById(USER1_ID);
        assertMatch(user, USER1);
    }

    @Test
    void findByIdNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                userService.findById(1));
    }

    @Test
    void findAll() throws Exception {
        List<User> all = userService.findAll();
        assertMatch(all, ADMIN, USER1, USER2);
    }

    @Test
    void findByEmail() {
        User user = userService.findByEmail(USER1_EMAIL);
        assertMatch(user, USER1);
    }

    @Test
    void findByEmailNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                userService.findByEmail(USER1_FALSE_EMAIL));
    }

    @Test
    void enable() {
        userService.enable(USER1_ID, false);
        assertFalse(userService.findById(USER1_ID).isEnabled());
        userService.enable(USER1_ID, true);
        assertTrue(userService.findById(USER1_ID).isEnabled());
    }

}
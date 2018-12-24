package ua.com.pollapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ua.com.pollapp.model.Restaurant;
import ua.com.pollapp.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.pollapp.testdata.RestaurantTestData.*;
import static ua.com.pollapp.testdata.UserTestData.ADMIN_ID;
import static ua.com.pollapp.testdata.UserTestData.USER_ID;


public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("restaurant").clear();
    }

    @Test
    public void create() {
        Restaurant newRestaurant = new Restaurant("CreateTest", "SomeAddress", "SomePhoneNumber");
        Restaurant created = restaurantService.create(newRestaurant, ADMIN_ID);
        newRestaurant.setId(created.getId());
        assertMatch(restaurantService.getAll(), newRestaurant, RESTAURANT1, RESTAURANT2, RESTAURANT3);
    }

    @Test
    public void delete() {
        restaurantService.delete(RESTRAUNT_ID, ADMIN_ID);
        assertMatch(restaurantService.getAll(), RESTAURANT2, RESTAURANT3);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.delete(RESTRAUNT_FALSE_ID, ADMIN_ID));
    }

    @Test
    void deleteWithWrongUserRole() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.delete(RESTRAUNT_ID, USER_ID));
    }

    @Test
    public void get() {
        Restaurant actual = restaurantService.get(RESTRAUNT_ID);
        assertMatch(actual, RESTAURANT1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.get(RESTRAUNT_FALSE_ID));
    }

    @Test
    public void update() {
        Restaurant updated = getUpdated();
        restaurantService.update(updated, ADMIN_ID);
        assertMatch(restaurantService.get(RESTRAUNT_ID), updated);
    }

    @Test
    public void getAll() {
        List<Restaurant> all = restaurantService.getAll();
        assertMatch(all, RESTAURANT1, RESTAURANT2, RESTAURANT3);
    }
}
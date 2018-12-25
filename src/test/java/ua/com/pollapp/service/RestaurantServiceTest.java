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
        Restaurant created = restaurantService.create(newRestaurant);
        newRestaurant.setId(created.getId());
        assertMatch(restaurantService.findAll(), RESTAURANT1, RESTAURANT2, RESTAURANT3, newRestaurant);
    }

    @Test
    public void update() {
        Restaurant updated = getUpdated();
        restaurantService.update(updated);
        assertMatch(restaurantService.findById(RESTRAUNT_ID), updated);
    }

    @Test
    public void delete() {
        restaurantService.delete(RESTRAUNT_ID);
        assertMatch(restaurantService.findAll(), RESTAURANT2, RESTAURANT3);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.delete(RESTRAUNT_FALSE_ID));
    }

    @Test
    public void findAll() {
        List<Restaurant> all = restaurantService.findAll();
        assertMatch(all, RESTAURANT1, RESTAURANT2, RESTAURANT3);
    }

    @Test
    public void findById() {
        Restaurant actual = restaurantService.findById(RESTRAUNT_ID);
        assertMatch(actual, RESTAURANT1);
    }

    @Test
    void findByIdNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.findById(RESTRAUNT_FALSE_ID));
    }

}
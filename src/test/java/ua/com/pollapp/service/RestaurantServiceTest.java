package ua.com.pollapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ua.com.pollapp.model.Restaurant;
import ua.com.pollapp.to.RestaurantTo;
import ua.com.pollapp.util.JpaUtil;
import ua.com.pollapp.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.pollapp.testdata.RestaurantTestData.*;


class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("restaurant").clear();
        jpaUtil.clear2ndLevelHibernateCache();

    }

    @Test
    void create() {
        Restaurant newRestaurant = new Restaurant("CreateTest", "SomeAddress", "SomePhoneNumber");
        Restaurant created = restaurantService.create(newRestaurant);
        newRestaurant.setId(created.getId());
        assertMatch(restaurantService.findAll(), RESTAURANT1, RESTAURANT2, RESTAURANT3, newRestaurant);
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        restaurantService.update(updated);
        assertMatch(restaurantService.findById(RESTAURANT_ID), updated);
    }

    @Test
    void delete() {
        restaurantService.delete(RESTAURANT_ID);
        assertMatch(restaurantService.findAll(), RESTAURANT2, RESTAURANT3);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.delete(RESTAURANT_FALSE_ID));
    }

    @Test
    void findAll() {
        List<Restaurant> all = restaurantService.findAll();
        assertMatch(all, RESTAURANT1, RESTAURANT2, RESTAURANT3);
    }

    @Test
    void findById() {
        Restaurant actual = restaurantService.findById(RESTAURANT_ID);
        assertMatch(actual, RESTAURANT1);
    }

    @Test
    void findByIdNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.findById(RESTAURANT_FALSE_ID));
    }

    @Test
    void findAllRestaurantWithUpdatedMenu() {
        List<Restaurant> all = restaurantService.findAllRestaurantWithUpdatedMenu(LocalDate.of(2018, 12, 03));
        assertMatch(all, RESTAURANT2, RESTAURANT3);
    }

    @Test
    void countVotesByMenuDate() {
        List<RestaurantTo> all = restaurantService.countVotesByMenuDate(LocalDate.of(2018, 12, 03));
        assertMatchTo(all, RESTAURANT_TO_2, RESTAURANT_TO_3);
    }

    @Test
    void findAllRestaurantWithVotes() {
        List<Restaurant> all = restaurantService.findAllRestaurantWithVotes();
        assertMatch(all, RESTAURANT1, RESTAURANT2, RESTAURANT3);
    }
}
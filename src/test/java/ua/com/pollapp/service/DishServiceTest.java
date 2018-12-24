package ua.com.pollapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.pollapp.model.Dish;
import ua.com.pollapp.testdata.UserTestData;
import ua.com.pollapp.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.pollapp.testdata.DishTestData.*;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService dishService;

    @Test
    public void create() {
        Dish newDish = new Dish("TestDish");
        Dish created = dishService.create(newDish, UserTestData.ADMIN_ID);
        newDish.setId(created.getId());
        assertMatch(dishService.getAll(), DISH1, DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9, DISH10, DISH11, newDish);
    }

    @Test
    public void delete() {
        dishService.delete(DISH1_ID, UserTestData.ADMIN_ID);
        assertMatch(dishService.getAll(), DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9, DISH10, DISH11);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dishService.delete(DISH_FALSE_ID, UserTestData.ADMIN_ID));
    }

    @Test
    void deleteWithWrongUserRole() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dishService.delete(DISH1_ID, UserTestData.USER_ID));
    }

    @Test
    public void get() {
        Dish actual = dishService.get(DISH1_ID);
        assertMatch(actual, DISH1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dishService.get(DISH_FALSE_ID));
    }

    @Test
    public void update() {
        Dish updated = getUpdated();
        dishService.update(updated, UserTestData.ADMIN_ID);
        assertMatch(dishService.get(DISH1_ID), updated);
    }


    @Test
    void getAll() throws Exception {
        assertMatch(dishService.getAll(), DISHLIST);
    }

}
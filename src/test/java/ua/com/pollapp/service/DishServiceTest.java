package ua.com.pollapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.pollapp.model.Dish;
import ua.com.pollapp.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.pollapp.testdata.DishTestData.*;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService dishService;

    @Test
    public void create() {
        Dish newDish = new Dish("TestDish");
        Dish created = dishService.create(newDish);
        newDish.setId(created.getId());
        assertMatch(dishService.findAll(), DISH1, DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9, DISH10, DISH11, newDish);
    }

    @Test
    public void update() {
        Dish updated = getUpdated();
        dishService.update(updated);
        assertMatch(dishService.findById(DISH1_ID), updated);
    }

    @Test
    public void delete() {
        dishService.delete(DISH1_ID);
        assertMatch(dishService.findAll(), DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9, DISH10, DISH11);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dishService.delete(DISH_FALSE_ID));
    }

    @Test
    void findAll() throws Exception {
        assertMatch(dishService.findAll(), DISHLIST);
    }

    @Test
    public void findById() {
        Dish actual = dishService.findById(DISH1_ID);
        assertMatch(actual, DISH1);
    }

    @Test
    void findByIdNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dishService.findById(DISH_FALSE_ID));
    }

    @Test
    public void findByDishName() {
        Dish actual = dishService.findByDishName(DISH1_NAME);
        assertMatch(actual, DISH1);
    }

    @Test
    void findByDishNameNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dishService.findByDishName(DISH1_FALSE_NAME));
    }
}
package ua.com.pollapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.pollapp.model.Menu;
import ua.com.pollapp.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.pollapp.testdata.DishTestData.DISH1;
import static ua.com.pollapp.testdata.MenuTestData.*;
import static ua.com.pollapp.testdata.RestaurantTestData.RESTAURANT1;
import static ua.com.pollapp.testdata.RestaurantTestData.RESTAURANT_ID;


class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    MenuService menuService;

    @Test
    void create() {
        Menu newMenu = new Menu(RESTAURANT1, LocalDate.of(2018, 5, 22), DISH1, 333);
        Menu created = menuService.create(newMenu);
        newMenu.setId(created.getId());
        assertMatch(menuService.findAll(), newMenu, MENU1, MENU2, MENU3, MENU4, MENU5, MENU6, MENU7, MENU8, MENU9, MENU10,
                MENU11, MENU12, MENU13, MENU14, MENU15, MENU16, MENU17, MENU18, MENU19);
    }

    @Test
    void update() {
        Menu updated = getUpdated();
        menuService.update(updated);
        assertMatch(menuService.findById(MENU1_ID), updated);
    }

    @Test
    void delete() {
        menuService.delete(MENU1_ID);
        assertMatch(menuService.findAll(), MENU2, MENU3, MENU4, MENU5, MENU6, MENU7, MENU8, MENU9, MENU10,
                MENU11, MENU12, MENU13, MENU14, MENU15, MENU16, MENU17, MENU18, MENU19);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                menuService.delete(MENU_FALSE_ID));
    }

    @Test
    void findAll() {
        List<Menu> all = menuService.findAll();
        assertMatch(all, MENULIST);
    }

    @Test
    void findById() {
        Menu actual = menuService.findById(MENU1_ID);
        assertMatch(actual, MENU1);
    }

    @Test
    void findByIdNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                menuService.findById(MENU_FALSE_ID));
    }


    @Test
    void findByRestaurant() {
        List<Menu> menuByRestaurant = menuService.findByRestaurant(RESTAURANT_ID);
        assertMatch(menuByRestaurant, MENU1, MENU2, MENU3, MENU4, MENU5, MENU6);
    }

    @Test
    void findByDate() {
        List<Menu> menuByDate = menuService.findByDate(LocalDate.of(2018, 12, 01));
        assertMatch(menuByDate, MENU1, MENU2, MENU3, MENU7, MENU8, MENU9, MENU13, MENU14);
    }

    @Test
    void getTodayMenuByRestaurantAndDate() {
        List<Menu> menuByDate = menuService.findByRestaurantAndDate(RESTAURANT_ID, LocalDate.of(2018, 12, 01));
        assertMatch(menuByDate, MENU1, MENU2, MENU3);
    }

}
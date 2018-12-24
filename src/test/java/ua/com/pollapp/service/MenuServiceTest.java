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
import static ua.com.pollapp.testdata.RestaurantTestData.RESTRAUNT_ID;
import static ua.com.pollapp.testdata.UserTestData.ADMIN_ID;
import static ua.com.pollapp.testdata.UserTestData.USER_ID;


public class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    MenuService menuService;

    @Test
    public void create() {
        Menu newMenu = new Menu(RESTAURANT1, LocalDate.of(2018, 5, 22), DISH1, 333);
        Menu created = menuService.create(newMenu, ADMIN_ID);
        newMenu.setId(created.getId());
        assertMatch(menuService.getAll(), newMenu, MENU1, MENU2, MENU3, MENU4, MENU5, MENU6, MENU7, MENU8, MENU9, MENU10,
                MENU11, MENU12, MENU13, MENU14, MENU15, MENU16);

    }

    @Test
    public void delete() {
        menuService.delete(MENU1_ID, ADMIN_ID);
        assertMatch(menuService.getAll(), MENU2, MENU3, MENU4, MENU5, MENU6, MENU7, MENU8, MENU9, MENU10,
                MENU11, MENU12, MENU13, MENU14, MENU15, MENU16);

    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                menuService.delete(MENU_FALSE_ID, ADMIN_ID));
    }

    @Test
    void deleteWithWrongUserRole() throws Exception {
        assertThrows(NotFoundException.class, () ->
                menuService.delete(MENU1_ID, USER_ID));
    }

    @Test
    public void get() {
        Menu actual = menuService.get(MENU1_ID);
        assertMatch(actual, MENU1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                menuService.get(MENU_FALSE_ID));
    }

    @Test
    public void update() {
        Menu updated = getUpdated();
        menuService.update(updated, ADMIN_ID);
        assertMatch(menuService.get(MENU1_ID), updated);
    }

    @Test
    public void getAll() {
        List<Menu> all = menuService.getAll();
        assertMatch(all, MENULIST);
    }


    @Test
    public void getByRestaurant() {
        List<Menu> menuByRestaurant = menuService.getByRestaurant(RESTRAUNT_ID);
        assertMatch(menuByRestaurant, MENU1, MENU2, MENU3, MENU4, MENU5, MENU6);
    }

    @Test
    public void getByDate() {
        List<Menu> menuByDate = menuService.getByDate(LocalDate.of(2018, 12, 01));
        assertMatch(menuByDate, MENU1, MENU2, MENU3, MENU7, MENU8, MENU9, MENU13, MENU14);
    }

    @Test
    public void getTodayMenuByRestaurantAndDate() {
        Menu newMenu = new Menu(RESTAURANT1, LocalDate.now(), DISH1, 333);
        Menu created = menuService.create(newMenu, ADMIN_ID);
        newMenu.setId(created.getId());
        List<Menu> menuByDate = menuService.getTodayMenuByRestaurant(RESTRAUNT_ID);
        assertMatch(menuByDate, newMenu);
    }

}
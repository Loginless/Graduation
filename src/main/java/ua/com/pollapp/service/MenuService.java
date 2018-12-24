package ua.com.pollapp.service;

import ua.com.pollapp.model.Dish;
import ua.com.pollapp.model.Menu;
import ua.com.pollapp.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {

    Menu create(Menu menu);

    void update(Menu menu) throws NotFoundException;

    void delete(int menuId) throws NotFoundException;

    List<Menu> findAll();

    Menu findById(int menuId) throws NotFoundException;

    List<Menu> findByRestaurant(int restaurantId);

    List<Menu> findByDate(LocalDate date);

    List<Menu> findByRestaurantAndDate(int restaurantId, LocalDate date);

}

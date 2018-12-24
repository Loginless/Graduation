package ua.com.pollapp.service;

import ua.com.pollapp.model.Dish;
import ua.com.pollapp.util.exception.NotFoundException;

import java.util.List;

public interface DishService {

    Dish create(Dish dish);

    void update(Dish dish) throws NotFoundException;

    void delete(int dishId) throws NotFoundException;

    List<Dish> findAll();

    Dish findById(int dishId) throws NotFoundException;

    Dish findByDishName(String dishName) throws NotFoundException;

}

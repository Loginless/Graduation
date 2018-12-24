package ua.com.pollapp.service;

import ua.com.pollapp.model.Restaurant;
import ua.com.pollapp.util.exception.NotFoundException;

import java.util.List;

public interface RestaurantService {

    Restaurant create(Restaurant restaurant);

    void update(Restaurant restaurant) throws NotFoundException;

    void delete(int restaurantId) throws NotFoundException;

    List<Restaurant> findAll();

    Restaurant findById(int restaurantId) throws NotFoundException;

}

package ua.com.pollapp.service;

import ua.com.pollapp.model.Restaurant;
import ua.com.pollapp.to.RestaurantTo;
import ua.com.pollapp.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RestaurantService {

    Restaurant create(Restaurant restaurant);

    void update(Restaurant restaurant) throws NotFoundException;

    void delete(int restaurantId) throws NotFoundException;

    List<Restaurant> findAll();

    Restaurant findById(int restaurantId) throws NotFoundException;

    List<Restaurant> findAllRestaurantWithUpdatedMenu(LocalDate menuDate);

    List<RestaurantTo> countVotesByMenuDate(LocalDate menuDate);

    List<RestaurantTo> countVotesForToday();
}

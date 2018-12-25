package ua.com.pollapp.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.com.pollapp.model.Restaurant;
import ua.com.pollapp.repository.RestaurantRepository;
import ua.com.pollapp.util.exception.NotFoundException;

import java.util.List;

import static ua.com.pollapp.util.ValidationUtil.checkNotFoundWithId;

@Service("restaurantService")
public class RestaurantServiceImpl implements RestaurantService {

    RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @CacheEvict(value = "restaurant", allEntries = true)
    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant mustn't be null");
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurant", allEntries = true)
    @Override
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    @Override
    public void delete(int restaurantId) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.deleteById(restaurantId) != 0, restaurantId);
    }

    @Cacheable("restaurant")
    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant findById(int restaurantId) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.findById(restaurantId).orElse(null), restaurantId);
    }
}

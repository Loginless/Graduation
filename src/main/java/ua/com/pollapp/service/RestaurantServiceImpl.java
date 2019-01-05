package ua.com.pollapp.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.com.pollapp.model.Restaurant;
import ua.com.pollapp.repository.RestaurantRepository;
import ua.com.pollapp.repository.VoteRepository;
import ua.com.pollapp.to.RestaurantTo;
import ua.com.pollapp.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ua.com.pollapp.util.ValidationUtil.checkNotFoundWithId;

@Service("restaurantService")
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;


    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
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

    @CacheEvict(value = "restaurant", allEntries = true)
    @Override
    public void delete(int restaurantId) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.deleteById(restaurantId) != 0, restaurantId);
    }

    @Cacheable("restaurant")
    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Cacheable("restaurant")
    @Override
    public Restaurant findById(int restaurantId) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.findById(restaurantId).orElse(null), restaurantId);
    }

    @Override
    public List<Restaurant> findAllRestaurantWithVotes() {
        return restaurantRepository.findAllRestaurantWithVotes();
    }

    @Cacheable("restaurant")
    @Override
    public List<Restaurant> findAllRestaurantWithUpdatedMenu(LocalDate menuDate) {
        return restaurantRepository.findAllRestaurantWithUpdatedMenu(menuDate);
    }

    @Override
    public List<RestaurantTo> countVotesByMenuDate(LocalDate menuDate) {
        List<Restaurant> restaurants = findAllRestaurantWithUpdatedMenu(menuDate);
        return restaurants
                .stream()
                .map(restaurant -> new RestaurantTo(restaurant, voteRepository.countAllByRestaurantIdAndVoteDate(restaurant.getId(), menuDate)))
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantTo> countVotesForToday() {
        List<Restaurant> restaurants = findAllRestaurantWithUpdatedMenu(LocalDate.now());
        return restaurants
                .stream()
                .map(restaurant -> new RestaurantTo(restaurant, voteRepository.countAllByRestaurantIdAndVoteDate(restaurant.getId(), LocalDate.now())))
                .collect(Collectors.toList());
    }

}

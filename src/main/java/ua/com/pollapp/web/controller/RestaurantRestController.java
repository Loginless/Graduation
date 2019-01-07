package ua.com.pollapp.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.com.pollapp.model.Restaurant;
import ua.com.pollapp.service.RestaurantService;
import ua.com.pollapp.to.RestaurantTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ua.com.pollapp.util.ValidationUtil.assureIdConsistent;
import static ua.com.pollapp.util.ValidationUtil.checkNew;


@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {

    public static final String REST_URL = "/rest/restaurants";
    private static final Logger LOG = LoggerFactory.getLogger(RestaurantRestController.class);

    @Autowired
    RestaurantService restaurantService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        LOG.info("create restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantService.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int restaurantId) {
        LOG.info("update restaurant {} with id={}", restaurant, restaurantId);
        assureIdConsistent(restaurant, restaurantId);
        restaurantService.update(restaurant);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int restaurantId) {
        LOG.info("delete {}", restaurantId);
        restaurantService.delete(restaurantId);
    }

    @GetMapping(value = "/restaurant")
    public List<Restaurant> getAll() {
        LOG.info("get all restaurants with votes");
        return restaurantService.findAll();
    }

    @GetMapping(value = "/{restaurantId}")
    public Restaurant get(@PathVariable("restaurantId") int restaurantId) {
        LOG.info("get {}", restaurantId);
        return restaurantService.findById(restaurantId);
    }

    @GetMapping(value = "/byMenuDate")
    public List<Restaurant> getRestaurantsByMenuDate(
            @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate menuDate){
        LOG.info("get all restaurants by menu date {}", menuDate);
        return restaurantService.findAllRestaurantWithUpdatedMenu(menuDate);
    }

    @GetMapping(value = "/restaurantVotes")
    public List<RestaurantTo> getAllRestaurantsWithVotes() {
        LOG.info("get all restaurants with votes");
        return restaurantService.countVotesForToday();
    }



}

package ua.com.pollapp.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.com.pollapp.model.Restaurant;
import ua.com.pollapp.service.RestaurantService;
import ua.com.pollapp.to.RestaurantTo;

import java.net.URI;
import java.util.List;

import static ua.com.pollapp.util.ValidationUtil.assureIdConsistent;
import static ua.com.pollapp.util.ValidationUtil.checkNew;


@RestController
@RequestMapping(RestaurantRestController.REST_URL)
public class RestaurantRestController {

    public static final String REST_URL = "/rest/restaurants";

    private static final Logger LOG = LoggerFactory.getLogger(RestaurantRestController.class);

    @Autowired
    RestaurantService restaurantService;

    @GetMapping(value = "/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("restaurantId") int restaurantId) {
        LOG.info("get {}", restaurantId);
        return restaurantService.findById(restaurantId);
    }

    @GetMapping(value = "/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        LOG.info("get all restaurants with votes");
        return restaurantService.findAll();
    }

    @GetMapping(value = "/restaurantVotes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantTo> countVotesForToday() {
        LOG.info("get all restaurants with votes");
        return restaurantService.countVotesForToday();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        LOG.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantService.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        LOG.info("delete {}", id);
        restaurantService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant menu, @PathVariable("id") int id) {
        LOG.info("update {} with id={}", menu, id);
        assureIdConsistent(menu, id);
        restaurantService.update(menu);
    }


}

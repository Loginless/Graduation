package ua.com.pollapp.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.com.pollapp.model.Dish;
import ua.com.pollapp.service.DishService;

import java.net.URI;
import java.util.List;

import static ua.com.pollapp.util.ValidationUtil.assureIdConsistent;
import static ua.com.pollapp.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {

    public static final String REST_URL = "/rest/dishes";
    private static final Logger LOG = LoggerFactory.getLogger(DishRestController.class);

    @Autowired
    private DishService dishService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@RequestBody Dish dish) {
        LOG.info("create dish {}", dish);
        checkNew(dish);
        Dish created = dishService.create(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish, @PathVariable("id") int dishId) {
        LOG.info("update dish {} with id={}", dish, dishId);
        assureIdConsistent(dish, dishId);
        dishService.update(dish);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int dishId) {
        LOG.info("delete dish {}", dishId);
        dishService.delete(dishId);
    }

    @GetMapping()
    public List<Dish> getAll() {
        LOG.info("get all dishes");
        return dishService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Dish get(@PathVariable("id") int dishId) {
        LOG.info("get dish {}", dishId);
        return dishService.findById(dishId);
    }

    @GetMapping(value = "/by")
    public Dish getByName(@RequestParam("dishName") String dishName) {
        LOG.info("get dish by dishName {}", dishName);
        return dishService.findByDishName(dishName);
    }

}

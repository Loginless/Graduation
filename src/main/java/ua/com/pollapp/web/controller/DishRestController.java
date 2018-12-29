package ua.com.pollapp.web.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.com.pollapp.model.Dish;
import ua.com.pollapp.service.DishService;

import java.net.URI;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ua.com.pollapp.util.ValidationUtil.assureIdConsistent;
import static ua.com.pollapp.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(DishRestController.REST_URL)
public class DishRestController {

    public static final String REST_URL = "/rest/dishes";
    private static final Logger LOG = getLogger(DishRestController.class);

    @Autowired
    private DishService dishService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll() {
        LOG.info("getAll");
        return dishService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("id") int id) {
        LOG.info("get {}", id);
        return dishService.findById(id);
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish getByName(@RequestParam("name") String dishName) {
        LOG.info("getByEmail {}", dishName);
        return dishService.findByDishName(dishName);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        LOG.info("delete {}", id);
        dishService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@RequestBody Dish dish) {
        LOG.info("create {}", dish);
        checkNew(dish);
        Dish created = dishService.create(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish, @PathVariable("id") int id) {
        LOG.info("update {} with id={}", dish, id);
        assureIdConsistent(dish, id);
        dishService.update(dish);
    }
}

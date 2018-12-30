package ua.com.pollapp.web.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.com.pollapp.model.Menu;
import ua.com.pollapp.service.MenuService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ua.com.pollapp.util.ValidationUtil.assureIdConsistent;
import static ua.com.pollapp.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(MenuRestController.REST_URL)
public class MenuRestController {

    public static final String REST_URL = "/rest/menu";
    private static final Logger LOG = getLogger(Menu.class);

    @Autowired
    private MenuService menuService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAll() {
        LOG.info("getAll");
        return menuService.findAll();
    }

    @GetMapping(value = "/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("menuId") int menuId) {
        LOG.info("get {}", menuId);
        return menuService.findById(menuId);
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getByRestaurantId(@RequestParam(value = "restaurantId") int restaurantId) {
        LOG.info("get all menus by restaurant ID {}", restaurantId);
        return menuService.findByRestaurant(restaurantId);
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getByDate(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LOG.info("get all menus by date{}", date);
        return menuService.findByDate(date);
    }

    @GetMapping(value = "/byIdDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getByRestaurantIdAndDate(
            @RequestParam(value = "restaurantId") int restaurantId,
            @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LOG.info("get all menus by restaurant ID {} and date{}", restaurantId, date);
        return menuService.findByRestaurantAndDate(restaurantId, date);
    }


    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        LOG.info("delete {}", id);
        menuService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@RequestBody Menu menu) {
        LOG.info("create {}", menu);
        checkNew(menu);
        Menu created = menuService.create(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Menu menu, @PathVariable("id") int id) {
        LOG.info("update {} with id={}", menu, id);
        assureIdConsistent(menu, id);
        menuService.update(menu);
    }

}

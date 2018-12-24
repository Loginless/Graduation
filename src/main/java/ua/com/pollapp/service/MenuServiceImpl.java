package ua.com.pollapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.com.pollapp.model.Menu;
import ua.com.pollapp.repository.MenuRepository;
import ua.com.pollapp.repository.RestaurantRepository;
import ua.com.pollapp.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ua.com.pollapp.util.ValidationUtil.checkNotFoundWithId;

@Service("menuService")
public class MenuServiceImpl extends MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;


    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Menu create(Menu menu) {
        Assert.notNull(menu, "menu mustn't be null");
        return menuRepository.save(menu);
    }

    @Override
    public void update(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        checkNotFoundWithId(menuRepository.save(menu), menu.getId());
    }

    @Override
    public void delete(int menuId) throws NotFoundException {
        checkNotFoundWithId(menuRepository.deleteById(menuId) != 0, menuId);
    }

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Override
    public Menu findById(int menuId) throws NotFoundException {
        return checkNotFoundWithId(menuRepository.findById(menuId).orElse(null), menuId);
    }

    @Override
    public List<Menu> findByRestaurant(int restaurantId) {
        return menuRepository.findByRestaurant(restaurantRepository.getOne(restaurantId));
    }

    @Override
    public List<Menu> findByDate(LocalDate date) {
        return menuRepository.findByDate(date);
    }

    @Override
    public List<Menu> findByRestaurantAndDate(int restaurantId, LocalDate date) {
        return menuRepository.findByRestaurantAndDate(restaurantRepository.getOne(restaurantId), date);
    }

}

package ua.com.pollapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.com.pollapp.model.Dish;
import ua.com.pollapp.repository.DishRepository;
import ua.com.pollapp.util.exception.NotFoundException;

import java.util.List;

import static ua.com.pollapp.util.ValidationUtil.checkNotFoundWithId;

@Service("dishService")
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }


    @Override
    public Dish create(Dish dish) {
        Assert.notNull(dish, "dish mustn't be null");
        return dishRepository.save(dish);
    }

    @Override
    public void update(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(dishRepository.save(dish), dish.getId());
    }

    @Override
    public void delete(int dishId) throws NotFoundException {
        checkNotFoundWithId(dishRepository.deleteById(dishId) != 0, dishId);
    }

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public Dish findById(int dishId) throws NotFoundException {
        return checkNotFoundWithId(dishRepository.findById(dishId).orElse(null), dishId);
    }

    @Override
    public Dish findByDishName(String dishName) throws NotFoundException {
        Assert.notNull(dishRepository.findByDishName(dishName).orElse(null), "dish mustn't be null");
        return dishRepository.findByDishName(dishName).orElse(null);
    }
}

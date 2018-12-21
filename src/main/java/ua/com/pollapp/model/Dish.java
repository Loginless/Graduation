package ua.com.pollapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = "dish_name", name = "dish_unique_dish_name_idx")})
public class Dish extends AbstractBaseEntity {

    @Column(name = "dish_name")
    private String dishName;

    public Dish() {
    }

    public Dish(String dishName) {
        this.dishName = dishName;
    }

    public Dish(int id, String dishName) {
        this.id = id;
        this.dishName = dishName;
    }

    public Dish(Dish dish) {
        this(dish.getId(), dish.getDishName());
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
}

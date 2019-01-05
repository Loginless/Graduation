package ua.com.pollapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date"}, name = "menu_unique_restaurant_id_date_time_idx")})
public class Menu extends AbstractBaseEntity {

    @Column(name = "date", columnDefinition = "date default now()")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "dish_price")
    private int dishPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnore
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(Integer id, Restaurant restaurant, LocalDate date, Dish dish, int dishPrice) {
        super(id);
        this.date = date;
        this.dish = dish;
        this.dishPrice = dishPrice;
        this.restaurant = restaurant;
    }

    public Menu(Restaurant restaurant, LocalDate date, Dish dish, int dishPrice) {
        this.date = date;
        this.dish = dish;
        this.dishPrice = dishPrice;
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate registered) {
        this.date = registered;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(int dishPrice) {
        this.dishPrice = dishPrice;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "date=" + date +
                ", dishPrice=" + dishPrice +
                ", id=" + id +
                '}';
    }
}

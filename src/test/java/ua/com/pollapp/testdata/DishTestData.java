package ua.com.pollapp.testdata;

import ua.com.pollapp.model.AbstractBaseEntity;
import ua.com.pollapp.model.Dish;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DishTestData {
    public static final int DISH1_ID = AbstractBaseEntity.START_SEQ + 6;
    public static final int DISH_FALSE_ID = AbstractBaseEntity.START_SEQ + 100;
    public static final String DISH1_NAME = "Steak";
    public static final String DISH1_FALSE_NAME = "Antricot";


    public static final Dish DISH1 = new Dish(100006, "Steak");
    public static final Dish DISH2 = new Dish(100007, "Fish");
    public static final Dish DISH3 = new Dish(100008, "Borsh");
    public static final Dish DISH4 = new Dish(100009, "Potato");
    public static final Dish DISH5 = new Dish(100010, "Salad");
    public static final Dish DISH6 = new Dish(100011, "Juice");
    public static final Dish DISH7 = new Dish(100012, "Ramen");
    public static final Dish DISH8 = new Dish(100013, "Noodles");
    public static final Dish DISH9 = new Dish(100014, "Rice");
    public static final Dish DISH10 = new Dish(100015, "Soup");
    public static final Dish DISH11 = new Dish(100016, "Chicken with Potato");

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "Обновленное блюда");
    }

    public static final List<Dish> DISHLIST = List.of(DISH1, DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9, DISH10, DISH11);

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).isEqualTo(expected);
    }


}

package ua.com.pollapp.testdata;

import ua.com.pollapp.model.AbstractBaseEntity;
import ua.com.pollapp.model.Restaurant;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class RestaurantTestData {
    public static final int RESTRAUNT_ID = AbstractBaseEntity.START_SEQ + 3;
    public static final int RESTRAUNT_FALSE_ID = AbstractBaseEntity.START_SEQ + 200;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTRAUNT_ID, "Games with Fire", "Kiev, Khreschatyk, 22", "+380445223456");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTRAUNT_ID + 1, "Menya Musashi", "Kiev, Peremohy Ave, 31", "+380442234356");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTRAUNT_ID + 2, "Very Well", "Kiev, Borshagivska str, 22", "+380442231326");

    public static Restaurant getUpdated() {
        return new Restaurant(RESTRAUNT_ID, "Updated Rest", "Updated address", "Updated PhoneNumber");
    }


    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menu", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menu", "votes").isEqualTo(expected);
    }

}

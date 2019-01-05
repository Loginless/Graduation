package ua.com.pollapp.testdata;

import org.springframework.test.web.servlet.ResultMatcher;
import ua.com.pollapp.model.AbstractBaseEntity;
import ua.com.pollapp.model.Restaurant;
import ua.com.pollapp.to.RestaurantTo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.com.pollapp.TestUtil.readFromJsonMvcResult;
import static ua.com.pollapp.TestUtil.readListFromJsonMvcResult;


public class RestaurantTestData {
    public static final int RESTAURANT_ID = AbstractBaseEntity.START_SEQ + 3;
    public static final int RESTAURANT_FALSE_ID = AbstractBaseEntity.START_SEQ + 200;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT_ID, "Games with Fire", "Kiev, Khreschatyk, 22", "+380445223456");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT_ID + 1, "Menya Musashi", "Kiev, Peremohy Ave, 31", "+380442234356");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT_ID + 2, "Very Well", "Kiev, Borshagivska str, 22", "+380442231326");

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "Updated Rest", "Updated address", "Updated PhoneNumber");
    }

    public static final RestaurantTo RESTAURANT_TO_2 = new RestaurantTo(RESTAURANT2, 2L);
    public static final RestaurantTo RESTAURANT_TO_3 = new RestaurantTo(RESTAURANT3, 0L);
    public static final RestaurantTo RESTAURANT_TO_CURRENT_DATE = new RestaurantTo(RESTAURANT3, 1L);


    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menu", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menu", "votes").isEqualTo(expected);
    }

    public static void assertMatchTo(Iterable<RestaurantTo> actual, RestaurantTo... expected) {
        assertMatchTo(actual, List.of(expected));
    }

    public static void assertMatchTo(Iterable<RestaurantTo> actual, Iterable<RestaurantTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields().isEqualTo(expected);
    }

    public static ResultMatcher getRestaurantMatcher(Restaurant... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Restaurant.class), List.of(expected));
    }

    public static ResultMatcher getRestaurantMatcher(Restaurant expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher getToMatcher(RestaurantTo... expected) {
        return getToMatcher(List.of(expected));
    }

    public static ResultMatcher getToMatcher(Iterable<RestaurantTo> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, RestaurantTo.class)).isEqualTo(expected);
    }

}

package ua.com.pollapp.testdata;

import ua.com.pollapp.model.AbstractBaseEntity;
import ua.com.pollapp.model.Restaurant;
import ua.com.pollapp.model.Votes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.com.pollapp.testdata.RestaurantTestData.*;
import static ua.com.pollapp.testdata.UserTestData.*;


public class VotesTestData {

    public static final int VOTE1_ID = AbstractBaseEntity.START_SEQ + 33;
    public static final int DISH_FALSE_ID = AbstractBaseEntity.START_SEQ + 100;
    public static final Map<Restaurant, Long> VOTINGRESULTS = new HashMap<>() {{
        put(RESTAURANT2, 2L);
        put(RESTAURANT1, 1L);
    }};

    public static final Votes VOTE1 = new Votes(VOTE1_ID, USER, RESTAURANT1, LocalDateTime.of(2018, 12, 01, 10, 00, 00));
    public static final Votes VOTE2 = new Votes(VOTE1_ID + 1, USER1, RESTAURANT3, LocalDateTime.of(2018, 12, 01, 10, 20, 00));
    public static final Votes VOTE3 = new Votes(VOTE1_ID + 2, USER, RESTAURANT2, LocalDateTime.of(2018, 12, 02, 9, 32, 00));
    public static final Votes VOTE4 = new Votes(VOTE1_ID + 3, USER1, RESTAURANT2, LocalDateTime.of(2018, 12, 02, 9, 42, 00));
    public static final Votes VOTE5 = new Votes(VOTE1_ID + 4, ADMIN, RESTAURANT1, LocalDateTime.of(2018, 12, 02, 10, 33, 12));

    public static Votes getUpdated() {
        return new Votes(VOTE1_ID, USER, RESTAURANT2, LocalDateTime.of(2018, 12, 01, 10, 57, 32));
    }


    public static void assertMatch(Votes actual, Votes expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Votes> actual, Votes... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Votes> actual, Iterable<Votes> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Map<Restaurant, Long> actual, Map<Restaurant, Long> expected) {
        assertThat(actual).isEqualTo(expected);
    }

}

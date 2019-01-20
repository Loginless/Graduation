package ua.com.pollapp.testdata;

import org.springframework.test.web.servlet.ResultMatcher;
import ua.com.pollapp.model.AbstractBaseEntity;
import ua.com.pollapp.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.com.pollapp.TestUtil.readFromJsonMvcResult;
import static ua.com.pollapp.TestUtil.readListFromJsonMvcResult;
import static ua.com.pollapp.testdata.RestaurantTestData.*;
import static ua.com.pollapp.testdata.UserTestData.*;


public class VotesTestData {

    public static final int VOTE1_ID = AbstractBaseEntity.START_SEQ + 36;
    public static final int VOTE_FALSE_ID = AbstractBaseEntity.START_SEQ + 100;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, USER1, RESTAURANT1, LocalDate.of(2018, 12, 1));
    public static final Vote VOTE2 = new Vote(VOTE1_ID + 1, USER2, RESTAURANT3, LocalDate.of(2018, 12, 1));
    public static final Vote VOTE3 = new Vote(VOTE1_ID + 2, USER1, RESTAURANT2, LocalDate.of(2018, 12, 2));
    public static final Vote VOTE4 = new Vote(VOTE1_ID + 3, USER2, RESTAURANT2, LocalDate.of(2018, 12, 2));
    public static final Vote VOTE5 = new Vote(VOTE1_ID + 4, ADMIN, RESTAURANT1, LocalDate.of(2018, 12, 2));
    public static final Vote VOTE6 = new Vote(VOTE1_ID + 5, USER1, RESTAURANT2, LocalDate.of(2018, 12, 3));
    public static final Vote VOTE7 = new Vote(VOTE1_ID + 6, USER2, RESTAURANT2, LocalDate.of(2018, 12, 3));
    public static final Vote VOTE8 = new Vote(VOTE1_ID + 7, USER1, RESTAURANT2, LocalDate.now());

    public static Vote getUpdated() {
        return new Vote(VOTE8.getId(), USER1, RESTAURANT3, LocalDate.now());
    }

    public static Vote getCreated  = new Vote(VOTE1_ID + 8, USER2, RESTAURANT2, LocalDate.of(2018, 12, 03));


    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher getVoteMatcher(Vote... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Vote.class), List.of(expected));
    }

    public static ResultMatcher getVoteMatcher(Vote expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Vote.class), expected);
    }

}

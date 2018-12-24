package ua.com.pollapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.pollapp.model.Restaurant;
import ua.com.pollapp.model.Votes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ua.com.pollapp.testdata.RestaurantTestData.RESTAURANT1;
import static ua.com.pollapp.testdata.UserTestData.*;
import static ua.com.pollapp.testdata.VotesTestData.*;
import static ua.com.pollapp.testdata.VotesTestData.assertMatch;


public class VotesServiceTest extends AbstractServiceTest {

    @Autowired
    VotesService votesService;

    @Test
    public void create() {
        Votes newVote = new Votes(USER, RESTAURANT1, LocalDateTime.of(2018, 12, 03, 10, 00, 00));
        Votes created = votesService.create(newVote, ADMIN_ID);
        newVote.setId(created.getId());
        assertMatch(votesService.findAll(), VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, newVote);
    }

    @Test
    public void update() {
        Votes updated = getUpdated();
        votesService.update(updated, USER_ID);
        assertMatch(votesService.get(VOTE1_ID, USER_ID), updated);
    }

//    @Test
//    void updateNotFound() throws Exception {
//        NotFoundException e = assertThrows(NotFoundException.class, () -> votesService.update(VOTE1, ADMIN_ID));
//        assertEquals(e.getMessage(), "Not found entity with id=" + VOTE1_ID);
//    }

    @Test
    public void getAll() {
        List<Votes> all = votesService.findAll();
        assertMatch(all, VOTE1, VOTE2, VOTE3, VOTE4, VOTE5);
    }

    @Test
    public void countVotesByRestaurant() {
        LocalDate testDate = LocalDate.of(2018, 12, 02);
        Map<Restaurant, Long> votingResult = votesService.countVotesByDate(testDate);
        assertMatch(votingResult, VOTINGRESULTS);

    }

}

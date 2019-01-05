package ua.com.pollapp.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.pollapp.model.Vote;
import ua.com.pollapp.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.pollapp.testdata.RestaurantTestData.RESTAURANT1;
import static ua.com.pollapp.testdata.RestaurantTestData.RESTAURANT_ID;
import static ua.com.pollapp.testdata.UserTestData.USER1;
import static ua.com.pollapp.testdata.UserTestData.USER1_ID;
import static ua.com.pollapp.testdata.VotesTestData.*;


class VotesServiceTest extends AbstractServiceTest {

    @Autowired
    VotesService votesService;

    @Test
    void create() {
        LocalDate voteDate = LocalDate.now();
        LocalTime voteTime = LocalTime.of(10, 00, 00);
        LocalDateTime voteDateTime = LocalDateTime.of(voteDate, voteTime);
        Vote newVote = new Vote(USER1, RESTAURANT1, voteDate);
        Vote created = votesService.save(USER1_ID, RESTAURANT_ID, voteDateTime);
        newVote.setId(created.getId());
        assertMatch(votesService.findAll(), VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, VOTE7, newVote);
    }

    @Test
    void update() {
        Vote updated = getUpdated();
        votesService.save(updated.getUser().getId(), updated.getRestaurant().getId(), LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 20, 11)));
        assertMatch(votesService.findById(VOTE8.getId()), updated);
    }

    @Test
    void findAll() {
        List<Vote> all = votesService.findAll();
        assertMatch(all, VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, VOTE7, VOTE8);
    }

    @Test
    void findById() {
        Vote vote = votesService.findById(VOTE1_ID);
        assertMatch(vote, VOTE1);
    }

    @Test
    void findByIdNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                votesService.findById(VOTE_FALSE_ID));
    }

    @Test
    void findByDate() {
        List<Vote> votesByDate = votesService.findByDate(LocalDate.of(2018, 12, 01));
        assertMatch(votesByDate, VOTE1, VOTE2);
    }

    @Test
    void findByUserIdAndVoteDate() {
        Vote vote = votesService.findByUserIdAndVoteDate(USER1_ID, LocalDate.of(2018, 12, 01));
        assertMatch(vote, VOTE1);
    }

    @Test
    void findByRestaurantIdAndVoteDate() {
        List<Vote> votesByRestaurantIdAndVoteDate = votesService.findByRestaurantIdAndVoteDate(RESTAURANT_ID, LocalDate.of(2018, 12, 02));
        assertMatch(votesByRestaurantIdAndVoteDate, VOTE5);
    }

    @Test
    void countVotesByRestaurant() {
        LocalDate testDate = LocalDate.of(2018, 12, 02);
        Long votingResult = votesService.countVotes(RESTAURANT_ID + 1, testDate);
        Assertions.assertThat(votingResult).isEqualTo(2L);
    }

}

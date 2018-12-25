package ua.com.pollapp.service;

import ua.com.pollapp.model.Vote;
import ua.com.pollapp.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VotesService {

    Vote save(int userId, int restaurantId, LocalDateTime dateTime);

    void delete(int voteId) throws NotFoundException;

    List<Vote> findAll();

    Vote findById(int voteId) throws NotFoundException;

    List<Vote> findByDate(LocalDate voteDate);

    Vote findByUserIdAndVoteDate(int userId, LocalDate voteDate) throws NotFoundException;

    List<Vote> findByRestaurantIdAndVoteDate(int restaurantId, LocalDate date);

    Long countVotes(int restaurantId, LocalDate voteDate);


}

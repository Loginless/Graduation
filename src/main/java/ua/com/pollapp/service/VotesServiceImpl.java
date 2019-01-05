package ua.com.pollapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.com.pollapp.model.Restaurant;
import ua.com.pollapp.model.User;
import ua.com.pollapp.model.Vote;
import ua.com.pollapp.repository.RestaurantRepository;
import ua.com.pollapp.repository.UserRepository;
import ua.com.pollapp.repository.VoteRepository;
import ua.com.pollapp.util.exception.NotFoundException;
import ua.com.pollapp.util.exception.VoteTimeOutException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ua.com.pollapp.util.ValidationUtil.checkNotFound;
import static ua.com.pollapp.util.ValidationUtil.checkNotFoundWithId;


@Service("votesService")
public class VotesServiceImpl implements VotesService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    private static final LocalTime VOTE_DEAD_LINE = LocalTime.of(11, 00, 00);


    @Autowired
    public VotesServiceImpl(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Vote save(int userId, int restaurantId, LocalDateTime dateTime) {
        Optional<Vote> voteOptional = voteRepository.findByUserIdAndVoteDate(userId, dateTime.toLocalDate());
        if (voteOptional.isPresent()
                && dateTime.toLocalDate().equals(LocalDate.now())
                && dateTime.toLocalTime().isBefore(VOTE_DEAD_LINE)
                ) {
            Vote updateVote = voteOptional.get();
            return update(updateVote, restaurantId);
        } else if (!voteOptional.isPresent()) {
            return create(userId, restaurantId, dateTime);
        } else {
            throw new VoteTimeOutException("Vote Dead Line is: " + VOTE_DEAD_LINE);
        }
    }

    private Vote update(Vote vote, int restaurantId) {
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(vote, vote.getId());
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        vote.setRestaurant(restaurant);
        return voteRepository.save(vote);
    }

    private Vote create(int userId, int restaurantId, LocalDateTime dateTime) {
        User user = userRepository.getOne(userId);
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        Vote vote = new Vote(user, restaurant, dateTime.toLocalDate());
        return voteRepository.save(vote);
    }

    @Override
    public void delete(int voteId) {
        checkNotFoundWithId(voteRepository.delete(voteId) != 0, voteId);
    }

    @Override
    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    @Override
    public Vote findById(int voteId) {
        return checkNotFoundWithId(voteRepository.findById(voteId).orElse(null), voteId);
    }

    @Override
    public List<Vote> findByDate(LocalDate voteDate) {
        return voteRepository.findByVoteDate(voteDate);
    }

    @Override
    public Vote findByUserIdAndVoteDate(int userId, LocalDate voteDate) throws NotFoundException {
        return checkNotFound(voteRepository.findByUserIdAndVoteDate(userId, voteDate).orElse(null),
                "userId and voteDate =" + userId + ", " + voteDate);
    }

    @Override
    public List<Vote> findByRestaurantIdAndVoteDate(int restaurantId, LocalDate voteDate) {
        return voteRepository.findByRestaurantIdAndVoteDate(restaurantId, voteDate);
    }

    @Override
    public Long countVotes(int restaurantId, LocalDate voteDate) {
        return voteRepository.countAllByRestaurantIdAndVoteDate(restaurantId, voteDate);
    }

}


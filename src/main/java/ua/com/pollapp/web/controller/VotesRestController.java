package ua.com.pollapp.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.com.pollapp.model.Vote;
import ua.com.pollapp.service.VotesService;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ua.com.pollapp.util.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = VotesRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VotesRestController {

    public static final String REST_URL = "/rest/votes";
    private static final Logger LOG = LoggerFactory.getLogger(RestaurantRestController.class);

    LocalDateTime testTime = LocalDateTime.of(2019, 01, 05, 10, 00, 00);

    @Autowired
    private VotesService votesService;

    @PostMapping(value = "/for", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@RequestParam(value = "restaurantId") int restaurantId) {
        LOG.info("vote for restaurant with ID {}", restaurantId);
//        Vote created = votesService.save(authUserId(), restaurantId, LocalDateTime.now());
        Vote created = votesService.save(authUserId(), restaurantId, testTime);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int voteId) {
        LOG.info("delete vote by id {}", voteId);
        votesService.delete(voteId);
    }

    @GetMapping()
    public List<Vote> getAll() {
        LOG.info("get all votes");
        return votesService.findAll();
    }

    @GetMapping(value = "/{voteId}")
    public Vote get(@PathVariable("voteId") int voteId) {
        LOG.info("get vote by id {}", voteId);
        return votesService.findById(voteId);
    }

    @GetMapping(value = "/by")
    public List<Vote> getByDate(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LOG.info("get all votes by date{}", date);
        return votesService.findByDate(date);
    }

    @GetMapping(value = "/byUserAndDate")
    public Vote getByUserIdAndDate(@RequestParam(value = "userId") int userId, @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LOG.info("get all votes by userId {} and date{}", userId, date);
        return votesService.findByUserIdAndVoteDate(userId, date);
    }

    @GetMapping(value = "/byRestaurantAndDate")
    public List<Vote> getByRestaurantIdAndVoteDate(@RequestParam(value = "restaurantId") int restaurantId, @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LOG.info("get all votes by restaurantId{} and date{}", restaurantId, date);
        return votesService.findByRestaurantIdAndVoteDate(restaurantId, date);
    }

}

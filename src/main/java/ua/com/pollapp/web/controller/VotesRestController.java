package ua.com.pollapp.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.com.pollapp.model.Vote;
import ua.com.pollapp.service.VotesService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static ua.com.pollapp.util.SecurityUtil.authUserId;

@RestController
@RequestMapping(VotesRestController.REST_URL)
public class VotesRestController {

    public static final String REST_URL = "/rest/votes";
    private static final Logger LOG = LoggerFactory.getLogger(RestaurantRestController.class);

    @Autowired
    VotesService votesService;

    @PostMapping(value = "/restaurantId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@RequestParam(value = "restaurantId") int restaurantId) {
        LOG.info("vote for restaurant with ID {}", restaurantId);
        Vote created = votesService.save(authUserId(), restaurantId, LocalDateTime.now());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAll() {
        LOG.info("getAll");
        return votesService.findAll();
    }

    @GetMapping(value = "/{voteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable("voteId") int voteId) {
        LOG.info("get {}", voteId);
        return votesService.findById(voteId);
    }

}

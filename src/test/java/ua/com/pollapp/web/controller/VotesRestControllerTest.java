package ua.com.pollapp.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.com.pollapp.TestUtil;
import ua.com.pollapp.model.Vote;
import ua.com.pollapp.service.VotesService;
import ua.com.pollapp.web.AbstractControllerTest;
import ua.com.pollapp.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.com.pollapp.TestUtil.userHttpBasic;
import static ua.com.pollapp.testdata.RestaurantTestData.RESTAURANT2;
import static ua.com.pollapp.testdata.UserTestData.ADMIN;
import static ua.com.pollapp.testdata.UserTestData.USER1;
import static ua.com.pollapp.testdata.VotesTestData.*;

class VotesRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VotesRestController.REST_URL + '/';

    @Autowired
    protected VotesService votesService;

    @Test
    void testCreate() throws Exception {
        Vote created = getCreated;
        ResultActions action = mockMvc.perform(post(REST_URL + "for?restaurantId=" + RESTAURANT2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(USER1)))
                .andExpect(status().isCreated());

        Vote returned = TestUtil.readFromJsonResultActions(action, Vote.class);
        created.setId(returned.getId());
        assertMatch(returned, created);
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + VOTE1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(votesService.findAll(), VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, VOTE7, VOTE8);
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + VOTE_FALSE_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testWrongAuth() throws Exception {
        mockMvc.perform(delete(REST_URL + VOTE1_ID)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER1))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getVoteMatcher(VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, VOTE7, VOTE8));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE1_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getVoteMatcher(VOTE1));
    }

    @Test
    void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "by?")
                .param("date", "2018-12-03")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getVoteMatcher(VOTE6, VOTE7));
    }

    @Test
    void getByUserIdAndDate() throws Exception {
        mockMvc.perform(get(REST_URL + "byUserAndDate?")
                .param("userId", "100001")
                .param("date", "2018-12-03")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getVoteMatcher(VOTE6));
    }

    @Test
    void getByRestaurantIdAndVoteDate() throws Exception {
        mockMvc.perform(get(REST_URL + "byRestaurantAndDate?")
                .param("restaurantId", "100004")
                .param("date", "2018-12-02")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getVoteMatcher(VOTE3, VOTE4));
    }
}

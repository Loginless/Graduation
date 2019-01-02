package ua.com.pollapp.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.com.pollapp.TestUtil;
import ua.com.pollapp.model.Restaurant;
import ua.com.pollapp.service.RestaurantService;
import ua.com.pollapp.web.AbstractControllerTest;
import ua.com.pollapp.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static ua.com.pollapp.testdata.RestaurantTestData.*;

public class RestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantRestController.REST_URL + '/';

    @Autowired
    protected RestaurantService restaurantService;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RESTRAUNT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getRestaurantMatcher(RESTAURANT1));
    }

    @Test
    void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "restaurant"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getRestaurantMatcher(RESTAURANT1, RESTAURANT2, RESTAURANT3)));
    }

    @Test
    void getVotesForToday() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "restaurantVotes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getToMatcher(RESTAURANT_TO_CURRENT_DATE)));
    }

    @Test
    void testCreate() throws Exception {
        Restaurant expected = new Restaurant("Test", "Test", "Test");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Restaurant returned = TestUtil.readFromJsonResultActions(action, Restaurant.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
        assertMatch(restaurantService.findAll(), RESTAURANT1, RESTAURANT2, RESTAURANT3, expected);
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTRAUNT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.findAll(), RESTAURANT2, RESTAURANT3);
    }

    @Test
    void testUpdate() throws Exception {
        Restaurant updated = getUpdated();
        mockMvc.perform(put(REST_URL + RESTRAUNT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.findById(RESTRAUNT_ID), updated);
    }


}

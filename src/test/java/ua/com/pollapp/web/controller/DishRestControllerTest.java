package ua.com.pollapp.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.com.pollapp.TestUtil;
import ua.com.pollapp.model.Dish;
import ua.com.pollapp.service.DishService;
import ua.com.pollapp.web.AbstractControllerTest;
import ua.com.pollapp.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.com.pollapp.testdata.DishTestData.*;


class DishRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = DishRestController.REST_URL + '/';

    @Autowired
    protected DishService dishService;


    @Test
    void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getUserMatcher(DISH1, DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9, DISH10, DISH11)));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + DISH1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getUserMatcher(DISH1));
    }

    @Test
    void testGetByName() throws Exception {
        mockMvc.perform(get(REST_URL + "by?name=" + DISH1.getDishName()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getUserMatcher(DISH1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + DISH1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(dishService.findAll(), DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9, DISH10, DISH11);
    }

    @Test
    void testCreate() throws Exception {
        Dish expected = new Dish("New Dish");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Dish returned = TestUtil.readFromJsonResultActions(action, Dish.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
        assertMatch(dishService.findAll(), DISH1, DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9, DISH10, DISH11, expected);
    }

    @Test
    void testUpdate() throws Exception {
        Dish updated = new Dish(DISH1);
        updated.setDishName("UpdatedName");
        mockMvc.perform(put(REST_URL + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(dishService.findById(DISH1_ID), updated);
    }
}
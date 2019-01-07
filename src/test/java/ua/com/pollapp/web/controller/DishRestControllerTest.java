package ua.com.pollapp.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.com.pollapp.model.Dish;
import ua.com.pollapp.service.DishService;
import ua.com.pollapp.web.AbstractControllerTest;
import ua.com.pollapp.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.com.pollapp.testdata.DishTestData.*;
import static ua.com.pollapp.testdata.UserTestData.ADMIN;
import static ua.com.pollapp.TestUtil.*;
import static ua.com.pollapp.testdata.UserTestData.USER1;


class DishRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = DishRestController.REST_URL + '/';

    @Autowired
    protected DishService dishService;

    @Test
    void testCreate() throws Exception {
        Dish expected = new Dish("New Dish");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(userHttpBasic(ADMIN)));

        Dish returned = readFromJsonResultActions(action, Dish.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
        assertMatch(dishService.findAll(), DISH1, DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9, DISH10, DISH11, expected);
    }

    @Test
    void testUpdate() throws Exception {
        Dish updated = getUpdated();
        mockMvc.perform(put(REST_URL + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertMatch(dishService.findById(DISH1_ID), updated);
    }


    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + DISH1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(dishService.findAll(), DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9, DISH10, DISH11);
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + DISH_FALSE_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testWrongAuth() throws Exception {
        mockMvc.perform(delete(REST_URL + DISH1_ID)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
        .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDishMatcher(DISH1, DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9, DISH10, DISH11));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + DISH1_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDishMatcher(DISH1));
    }

    @Test
    void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL + DISH1_ID))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void testGetByName() throws Exception {
        mockMvc.perform(get(REST_URL + "by?dishName=" + DISH1.getDishName())
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDishMatcher(DISH1));
    }

}
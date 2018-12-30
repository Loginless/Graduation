package ua.com.pollapp.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.com.pollapp.TestUtil;
import ua.com.pollapp.model.Menu;
import ua.com.pollapp.service.MenuService;
import ua.com.pollapp.web.AbstractControllerTest;
import ua.com.pollapp.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.com.pollapp.testdata.DishTestData.DISH1;
import static ua.com.pollapp.testdata.MenuTestData.*;
import static ua.com.pollapp.testdata.RestaurantTestData.RESTAURANT1;
import static ua.com.pollapp.testdata.RestaurantTestData.RESTRAUNT_ID;


public class MenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MenuRestController.REST_URL + '/';

    @Autowired
    protected MenuService menuService;

    @Test
    void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getMenuMatcher(MENU1, MENU2, MENU3, MENU4, MENU5, MENU6, MENU7, MENU8, MENU9, MENU10,
                        MENU11, MENU12, MENU13, MENU14, MENU15, MENU16)));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MENU1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getMenuMatcher(MENU1));
    }

    @Test
    void testGetByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?")
                .param("date", "2018-12-02"))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getMenuMatcher(MENU4, MENU5, MENU6, MENU10, MENU11, MENU12, MENU15, MENU16));
    }

    @Test
    void testGetByRestaurantIdAndDate() throws Exception {
        mockMvc.perform(get(REST_URL + "byIdDate?")
                .param("date", "2018-12-02")
                .param("restaurantId", "" + RESTRAUNT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getMenuMatcher(MENU4, MENU5, MENU6));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MENU1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(menuService.findAll(), MENU2, MENU3, MENU4, MENU5, MENU6, MENU7, MENU8, MENU9, MENU10,
                MENU11, MENU12, MENU13, MENU14, MENU15, MENU16);
    }

    @Test
    void testCreate() throws Exception {
        Menu expected = new Menu(RESTAURANT1, LocalDate.of(2018, 12, 30), DISH1, 234);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Menu returned = TestUtil.readFromJsonResultActions(action, Menu.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
        assertMatch(menuService.findAll(), MENU1, MENU2, MENU3, MENU4, MENU5, MENU6, MENU7, MENU8, MENU9, MENU10,
                MENU11, MENU12, MENU13, MENU14, MENU15, MENU16, expected);
    }

    @Test
    void testUpdate() throws Exception {
        Menu updated = getUpdated();
        mockMvc.perform(put(REST_URL + MENU1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(menuService.findById(MENU1_ID), updated);
    }

}

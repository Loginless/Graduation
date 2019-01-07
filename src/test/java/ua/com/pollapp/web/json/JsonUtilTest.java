package ua.com.pollapp.web.json;

import org.junit.jupiter.api.Test;
import ua.com.pollapp.model.User;
import ua.com.pollapp.testdata.UserTestData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ua.com.pollapp.testdata.UserTestData.*;


class JsonUtilTest {

    @Test
    void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(ADMIN);
        System.out.println(json);
        User user = JsonUtil.readValue(json, User.class);
        assertMatch(user, ADMIN);
    }

    @Test
    void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(USERLIST);
        System.out.println(json);
        List<User> meals = JsonUtil.readValues(json, User.class);
        assertMatch(meals, USERLIST);
    }


    @Test
    void testWriteOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserTestData.USER1);
        System.out.println(json);
        assertFalse(json.contains("password"));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER1, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}
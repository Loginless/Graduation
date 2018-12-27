package ua.com.pollapp.web.json;

import org.junit.jupiter.api.Test;
import ua.com.pollapp.model.User;

import java.util.List;

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
}
package ua.com.pollapp.testdata;

import org.springframework.test.web.servlet.ResultMatcher;
import ua.com.pollapp.model.AbstractBaseEntity;
import ua.com.pollapp.model.Role;
import ua.com.pollapp.model.User;
import ua.com.pollapp.web.json.JsonUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.com.pollapp.TestUtil.*;


public class UserTestData {
    public static final int ADMIN_ID = AbstractBaseEntity.START_SEQ;
    public static final int USER1_ID = AbstractBaseEntity.START_SEQ + 1;
    public static final int USER2_ID = AbstractBaseEntity.START_SEQ + 2;

    public static final String USER1_EMAIL = "user1@yandex.ru";
    public static final String USER1_FALSE_EMAIL = "dfdf@yandex.ru";

    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);
    public static final User USER1 = new User(USER1_ID, "User_1", "user1@yandex.ru", "password1", Role.ROLE_USER);
    public static final User USER2 = new User(USER2_ID, "User_2", "user2@yandex.ru", "password2", Role.ROLE_USER);

    public static final List<User> USERLIST = List.of(ADMIN, USER1, USER2);


    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "votes",  "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "votes",  "password").isEqualTo(expected);
    }

    public static ResultMatcher getUserMatcher(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher getUserMatcher(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}

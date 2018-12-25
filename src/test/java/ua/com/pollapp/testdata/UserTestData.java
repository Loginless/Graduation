package ua.com.pollapp.testdata;

import ua.com.pollapp.model.AbstractBaseEntity;
import ua.com.pollapp.model.Role;
import ua.com.pollapp.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final int ADMIN_ID = AbstractBaseEntity.START_SEQ;
    public static final int USER_ID = AbstractBaseEntity.START_SEQ + 1;
    public static final int USER1_ID = AbstractBaseEntity.START_SEQ + 2;
    public static final String USER1_EMAIL = "user1@yandex.ru";
    public static final String USER1_FALSE_EMAIL = "dfdf@yandex.ru";


    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User USER1 = new User(USER1_ID, "User1", "user1@yandex.ru", "password1", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "votes");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "votes").isEqualTo(expected);
    }

//    public static ResultMatcher getUserMatcher(User... expected) {
//        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
//    }
//
//    public static ResultMatcher getUserMatcher(User expected) {
//        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
//    }
}

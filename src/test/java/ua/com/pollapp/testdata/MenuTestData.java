package ua.com.pollapp.testdata;

import org.springframework.test.web.servlet.ResultMatcher;
import ua.com.pollapp.model.Menu;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.com.pollapp.TestUtil.readFromJsonMvcResult;
import static ua.com.pollapp.TestUtil.readListFromJsonMvcResult;
import static ua.com.pollapp.model.AbstractBaseEntity.START_SEQ;
import static ua.com.pollapp.testdata.DishTestData.*;
import static ua.com.pollapp.testdata.RestaurantTestData.*;

public class MenuTestData {

    public static final int MENU1_ID = START_SEQ + 17;
    public static final int MENU_FALSE_ID = START_SEQ + 100;

    public static final Menu MENU1 = new Menu(MENU1_ID, RESTAURANT1, LocalDate.of(2018, 12, 0), DISH1, 150);
    public static final Menu MENU2 = new Menu(MENU1_ID + 1, RESTAURANT1, LocalDate.of(2018, 12, 1), DISH2, 80);
    public static final Menu MENU3 = new Menu(MENU1_ID + 2, RESTAURANT1, LocalDate.of(2018, 12, 1), DISH3, 90);
    public static final Menu MENU4 = new Menu(MENU1_ID + 3, RESTAURANT1, LocalDate.of(2018, 12, 2), DISH4, 150);
    public static final Menu MENU5 = new Menu(MENU1_ID + 4, RESTAURANT1, LocalDate.of(2018, 12, 2), DISH5, 80);
    public static final Menu MENU6 = new Menu(MENU1_ID + 5, RESTAURANT1, LocalDate.of(2018, 12, 2), DISH6, 90);
    public static final Menu MENU7 = new Menu(MENU1_ID + 6, RESTAURANT2, LocalDate.of(2018, 12, 1), DISH7, 125);
    public static final Menu MENU8 = new Menu(MENU1_ID + 7, RESTAURANT2, LocalDate.of(2018, 12, 1), DISH8, 135);
    public static final Menu MENU9 = new Menu(MENU1_ID + 8, RESTAURANT2, LocalDate.of(2018, 12, 1), DISH9, 80);
    public static final Menu MENU10 = new Menu(MENU1_ID + 9, RESTAURANT2, LocalDate.of(2018, 12, 2), DISH7, 125);
    public static final Menu MENU11 = new Menu(MENU1_ID + 10, RESTAURANT2, LocalDate.of(2018, 12, 2), DISH8, 135);
    public static final Menu MENU12 = new Menu(MENU1_ID + 11, RESTAURANT2, LocalDate.of(2018, 12, 2), DISH9, 80);
    public static final Menu MENU13 = new Menu(MENU1_ID + 12, RESTAURANT3, LocalDate.of(2018, 12, 1), DISH5, 122);
    public static final Menu MENU14 = new Menu(MENU1_ID + 13, RESTAURANT3, LocalDate.of(2018, 12, 1), DISH10, 50);
    public static final Menu MENU15 = new Menu(MENU1_ID + 14, RESTAURANT3, LocalDate.of(2018, 12, 2), DISH5, 122);
    public static final Menu MENU16 = new Menu(MENU1_ID + 15, RESTAURANT3, LocalDate.of(2018, 12, 2), DISH11, 134);
    public static final Menu MENU17 = new Menu(MENU1_ID + 16, RESTAURANT3, LocalDate.of(2018, 12, 3), DISH5, 333);
    public static final Menu MENU18 = new Menu(MENU1_ID + 17, RESTAURANT2, LocalDate.of(2018, 12, 3), DISH11, 444);
    public static final Menu MENU19 = new Menu(MENU1_ID + 18, RESTAURANT3, LocalDate.now(), DISH10, 333);

    public static final List<Menu> MENULIST = List.of(MENU1, MENU2, MENU3, MENU4, MENU5, MENU6, MENU7, MENU8, MENU9, MENU10,
            MENU11, MENU12, MENU13, MENU14, MENU15, MENU16, MENU17, MENU18, MENU19);

    public static Menu getCreated  = new Menu(RESTAURANT1, LocalDate.of(2018, 12, 30), DISH1, 234);


    public static Menu getUpdated() {
        return new Menu(MENU1_ID, RESTAURANT3, LocalDate.of(2018, 12, 23), DISH5, 555);
    }


    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishes", "restaurant");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertThat(actual).containsExactlyInAnyOrder(expected).usingElementComparatorIgnoringFields("dishes", "restaurant");
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected).usingElementComparatorIgnoringFields("dishes", "restaurant");
    }

    public static ResultMatcher getMenuMatcher(Menu... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Menu.class), List.of(expected));
    }

    public static ResultMatcher getMenuMatcher(Menu expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Menu.class), expected);
    }

}

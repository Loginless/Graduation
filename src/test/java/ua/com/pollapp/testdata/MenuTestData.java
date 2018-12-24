package ua.com.pollapp.testdata;

import ua.com.pollapp.model.Menu;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.com.pollapp.model.AbstractBaseEntity.START_SEQ;
import static ua.com.pollapp.testdata.DishTestData.*;
import static ua.com.pollapp.testdata.RestaurantTestData.*;

public class MenuTestData {

    public static final int MENU1_ID = START_SEQ + 17;
    public static final int MENU_FALSE_ID = START_SEQ + 100;

    public static final Menu MENU1 = new Menu(100017, RESTAURANT1, LocalDate.of(2018, 12, 01), DISH1, 150);
    public static final Menu MENU2 = new Menu(100018, RESTAURANT1, LocalDate.of(2018, 12, 01), DISH2, 80);
    public static final Menu MENU3 = new Menu(100019, RESTAURANT1, LocalDate.of(2018, 12, 01), DISH3, 90);
    public static final Menu MENU4 = new Menu(100020, RESTAURANT1, LocalDate.of(2018, 12, 02), DISH4, 150);
    public static final Menu MENU5 = new Menu(100021, RESTAURANT1, LocalDate.of(2018, 12, 02), DISH5, 80);
    public static final Menu MENU6 = new Menu(100022, RESTAURANT1, LocalDate.of(2018, 12, 02), DISH6, 90);
    public static final Menu MENU7 = new Menu(100023, RESTAURANT2, LocalDate.of(2018, 12, 01), DISH7, 125);
    public static final Menu MENU8 = new Menu(100024, RESTAURANT2, LocalDate.of(2018, 12, 01), DISH8, 135);
    public static final Menu MENU9 = new Menu(100025, RESTAURANT2, LocalDate.of(2018, 12, 01), DISH9, 80);
    public static final Menu MENU10 = new Menu(100026, RESTAURANT2, LocalDate.of(2018, 12, 02), DISH7, 125);
    public static final Menu MENU11 = new Menu(100027, RESTAURANT2, LocalDate.of(2018, 12, 02), DISH8, 135);
    public static final Menu MENU12 = new Menu(100028, RESTAURANT2, LocalDate.of(2018, 12, 02), DISH9, 80);
    public static final Menu MENU13 = new Menu(100029, RESTAURANT3, LocalDate.of(2018, 12, 01), DISH5, 122);
    public static final Menu MENU14 = new Menu(100030, RESTAURANT3, LocalDate.of(2018, 12, 01), DISH10, 50);
    public static final Menu MENU15 = new Menu(100031, RESTAURANT3, LocalDate.of(2018, 12, 02), DISH5, 122);
    public static final Menu MENU16 = new Menu(100032, RESTAURANT3, LocalDate.of(2018, 12, 02), DISH11, 134);

    public static final List<Menu> MENULIST = List.of(MENU1, MENU2, MENU3, MENU4, MENU5, MENU6, MENU7, MENU8, MENU9, MENU10,
            MENU11, MENU12, MENU13, MENU14, MENU15, MENU16);

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

}

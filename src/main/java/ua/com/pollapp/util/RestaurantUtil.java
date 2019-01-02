//package ua.com.pollapp.util;
//
//import ua.com.pollapp.model.Restaurant;
//import ua.com.pollapp.to.RestaurantTo;
//
//import java.time.LocalDate;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//
//import static java.util.stream.Collectors.toList;
//
//public class RestaurantUtil {
//
//    private RestaurantUtil() {
//    }
//
//    public static List<RestaurantTo> RestaurantListAsTo(Collection<Restaurant> restaurants, LocalDate date, Predicate<Restaurant> filter) {
//        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
//                .collect(
//                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
////                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
//                );
//
//        return meals.stream()
//                .filter(filter)
//                .map(meal -> createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
//                .collect(toList());
//    }
//
//    public List<RestaurantVoteCountTo> getWithVotesCountByDate(LocalDate date) {
//        List<Restaurant> restaurants = getByMenuDate(date);
//        return restaurants
//                .stream()
//                .map(r -> new RestaurantVoteCountTo(date, r, voteRepository.countAllByRestaurantIdAndDate(r.getId(), date)))
//                .collect(Collectors.toList());
//    }
//}
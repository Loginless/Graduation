package ua.com.pollapp.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.com.pollapp.model.Menu;
import ua.com.pollapp.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Override
    @Transactional
    Menu save(Menu menu);

    // return false if not found
    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int deleteById(@Param("id") int id);

    @EntityGraph(attributePaths = {"dish", "restaurant"})
    List<Menu> getByRestaurant(Restaurant restaurant, Sort sort);

    @EntityGraph(attributePaths = {"dish", "restaurant"})
    List<Menu> getByDate(LocalDate date);

    @EntityGraph(attributePaths = {"dish", "restaurant"})
    List<Menu> getByRestaurantAndDate(Restaurant restaurant, LocalDate date, Sort sort);

    // returns null if not found
    @Override
    @EntityGraph(attributePaths = {"dish", "restaurant"})
    Optional<Menu> findById(Integer menuId);

    // returns null if not found
    @Override
    @EntityGraph(attributePaths = {"dish", "restaurant"})
    List<Menu> findAll(Sort sort);

}

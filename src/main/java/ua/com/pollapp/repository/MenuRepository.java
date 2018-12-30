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

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int deleteById(@Param("id") int id);

    @Override
    @EntityGraph(attributePaths = {"dish", "restaurant"})
    Optional<Menu> findById(Integer menuId);

    @Override
    @EntityGraph(attributePaths = {"dish", "restaurant"})
    List<Menu> findAll(Sort sort);

    @EntityGraph(attributePaths = {"dish"})
    List<Menu> findByRestaurantId(int restaurantId);

    @EntityGraph(attributePaths = {"dish", "restaurant"})
    List<Menu> findByDate(LocalDate date);

    @EntityGraph(attributePaths = {"dish", "restaurant"})
    List<Menu> findByRestaurantAndDate(Restaurant restaurant, LocalDate date);


}

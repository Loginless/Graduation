package ua.com.pollapp.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.com.pollapp.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int deleteById(@Param("id") int id);

    @Override
    List<Restaurant> findAll(Sort sort);

    @Override
    @EntityGraph(attributePaths = {"menu", "votes"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Restaurant> findById(Integer restaurantId);

    @Transactional
    @EntityGraph(attributePaths = {"votes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.votes v")
    List<Restaurant> findAllRestaurantWithVotes();

    @Transactional
    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT distinct r FROM Restaurant r LEFT JOIN r.menu m WHERE m.date=?1")
    List<Restaurant> findAllRestaurantWithUpdatedMenu(LocalDate date);



}

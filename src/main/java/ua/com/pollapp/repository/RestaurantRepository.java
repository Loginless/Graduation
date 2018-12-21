package ua.com.pollapp.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.com.pollapp.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);

    // returns null if not found
    @Override
    @EntityGraph(attributePaths = {"menu", "votes"})
    Optional<Restaurant> findById(Integer integer);

    @Override
    @EntityGraph(attributePaths = {"menu", "votes"})
    List<Restaurant> findAll(Sort sort);

    // return false if not found
    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int deleteById(@Param("id") int id);

}

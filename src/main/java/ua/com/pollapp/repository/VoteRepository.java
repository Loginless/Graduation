package ua.com.pollapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.com.pollapp.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Override
    @Transactional
    Vote save(Vote vote);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);

    @Override
    @EntityGraph(attributePaths = {"user", "restaurant"})
    List<Vote> findAll();

    @Override
    @EntityGraph(attributePaths = {"user", "restaurant"})
    Optional<Vote> findById(Integer integer);

    @EntityGraph(attributePaths = {"user", "restaurant"})
    Optional<Vote> findByUserIdAndDate(int userId, LocalDate date);

    @EntityGraph(attributePaths = {"user", "restaurant"})
    List<Vote> findByDate(LocalDate date);

    @EntityGraph(attributePaths = {"user", "restaurant"})
    List<Vote> findByRestaurantIdAndDate(int restaurantId, LocalDate date);

    Long countAllByRestaurantIdAndDate(int restaurantId, LocalDate date);


}

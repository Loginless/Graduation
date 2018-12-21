package ua.com.pollapp.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.com.pollapp.model.Dish;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Override
    @Transactional
    Dish save(Dish dish);

    // return false if not found
    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int deleteById(@Param("id") int id);

    // returns null if not found
    Optional<Dish> findById(Integer integer);

    @Override
    List<Dish> findAll(Sort sort);

    // returns null if not found
    Optional<Dish> getByDishName(String dishName);

}

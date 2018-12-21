package ua.com.pollapp.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.com.pollapp.model.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    @Transactional
    User save(User user);

    // returns null if not found
    @Override
    Optional<User> findById(Integer id);

    // return false if not found
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Override
    List<User> findAll(Sort sort);

    // returns null if not found
    User getByEmail(String email);

}

package technofutur.backend.dal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import technofutur.backend.dal.entities.security.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query( "Select u " +
            "from User u " +
            "where u.username ilike :username")
    Optional<User> findByUsername(String username);
}

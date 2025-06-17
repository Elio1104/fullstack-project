package technofutur.backend.dal.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import technofutur.backend.dal.entities.security.UserAuth;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAuth, Long> {

    @Query( "Select u " +
            "from UserAuth u " +
            "where u.username ilike :username")
    Optional<UserAuth> findByUsername(String username);
}

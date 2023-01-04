package pl.markopolo.matchpredictor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<pl.markopolo.matchpredictor.models.User> findByUserName(String userName);
}

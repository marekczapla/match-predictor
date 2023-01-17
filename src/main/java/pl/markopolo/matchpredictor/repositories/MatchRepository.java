package pl.markopolo.matchpredictor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markopolo.matchpredictor.models.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
}

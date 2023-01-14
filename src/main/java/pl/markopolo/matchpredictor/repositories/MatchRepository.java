package pl.markopolo.matchpredictor.repositories;

import org.bouncycastle.crypto.agreement.jpake.JPAKERound1Payload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markopolo.matchpredictor.models.Matches;

@Repository
public interface MatchRepository extends JpaRepository<Matches, Long> {
}

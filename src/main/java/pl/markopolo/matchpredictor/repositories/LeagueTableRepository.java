package pl.markopolo.matchpredictor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markopolo.matchpredictor.models.LeagueTable;

@Repository
public interface LeagueTableRepository extends JpaRepository<LeagueTable, Long> {
}

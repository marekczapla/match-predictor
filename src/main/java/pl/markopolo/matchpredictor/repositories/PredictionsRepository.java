package pl.markopolo.matchpredictor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markopolo.matchpredictor.models.Predictions;

@Repository
public interface PredictionsRepository extends JpaRepository<Predictions, Long> {
}
